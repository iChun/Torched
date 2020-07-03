package me.ichun.mods.torched.common;

import me.ichun.mods.ichunutil.client.model.item.ItemModelRenderer;
import me.ichun.mods.ichunutil.common.network.PacketChannel;
import me.ichun.mods.torched.client.core.EventHandlerClient;
import me.ichun.mods.torched.client.particle.FlameParticle;
import me.ichun.mods.torched.client.render.ItemRenderTorchGun;
import me.ichun.mods.torched.client.render.ItemRenderTorchLauncher;
import me.ichun.mods.torched.client.render.RenderTorch;
import me.ichun.mods.torched.client.render.RenderTorchFirework;
import me.ichun.mods.torched.common.core.EventHandlerServer;
import me.ichun.mods.torched.common.entity.EntityTorch;
import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import me.ichun.mods.torched.common.item.ItemTorchFirework;
import me.ichun.mods.torched.common.item.ItemTorchGun;
import me.ichun.mods.torched.common.item.ItemTorchLauncher;
import me.ichun.mods.torched.common.item.crafting.RecipeTorchGun;
import me.ichun.mods.torched.common.packet.PacketKeyEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Torched.MOD_ID)
public class Torched
{
    public static final String MOD_NAME = "Torched";
    public static final String MOD_ID = "torched";
    public static final String PROTOCOL = "1";

    public static EventHandlerServer eventHandlerServer;
    public static EventHandlerClient eventHandlerClient;

    public static PacketChannel channel;

    public Torched()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityTypes.REGISTRY.register(bus);
        Items.REGISTRY.register(bus);
        Particles.REGISTRY.register(bus);
        Recipes.REGISTRY.register(bus);
        Sounds.REGISTRY.register(bus);

        MinecraftForge.EVENT_BUS.register(eventHandlerServer = new EventHandlerServer());

        channel = new PacketChannel(new ResourceLocation(MOD_ID, "channel"), PROTOCOL, PacketKeyEvent.class);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(this::onClientSetup);
            bus.addListener(this::onRegisterParticleFactory);
            bus.addListener(this::onModelBake);

            MinecraftForge.EVENT_BUS.register(eventHandlerClient = new EventHandlerClient());
        });
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityTypes.TORCH.get(), new RenderTorch.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityTypes.TORCH_FIREWORK.get(), new RenderTorchFirework.RenderFactory());

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> me.ichun.mods.ichunutil.client.core.EventHandlerClient::getConfigGui);
    }

    @OnlyIn(Dist.CLIENT)
    private void onRegisterParticleFactory(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particles.registerFactory(Particles.FLAME.get(), FlameParticle.Factory::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void onModelBake(ModelBakeEvent event)
    {
        event.getModelRegistry().put(new ModelResourceLocation("torched:torchlauncher", "inventory"), new ItemModelRenderer(ItemRenderTorchLauncher.INSTANCE));
        event.getModelRegistry().put(new ModelResourceLocation("torched:torchgun", "inventory"), new ItemModelRenderer(ItemRenderTorchGun.INSTANCE));
    }

    public static class EntityTypes
    {
        private static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);

        public static final RegistryObject<EntityType<EntityTorch>> TORCH = REGISTRY.register("torch", () -> EntityType.Builder.create(EntityTorch::new, EntityClassification.MISC)
                .size(0.5F, 0.5F)
                .setTrackingRange(64)
                .setUpdateInterval(20)
                .setShouldReceiveVelocityUpdates(true)
                .build("from " + MOD_NAME + ". Ignore this.")
        );
        public static final RegistryObject<EntityType<EntityTorchFirework>> TORCH_FIREWORK = REGISTRY.register("torch_firework", () -> EntityType.Builder.create(EntityTorchFirework::new, EntityClassification.MISC)
                .size(1.0F, 1.0F)
                .setTrackingRange(128)
                .setUpdateInterval(2)
                .setShouldReceiveVelocityUpdates(true)
                .build("from " + MOD_NAME + ". Ignore this.")
        );
    }

    public static class Items
    {
        private static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

        public static final RegistryObject<ItemTorchGun> TORCH_GUN = REGISTRY.register("torchgun", () -> new ItemTorchGun(new Item.Properties().maxDamage(65).group(ItemGroup.TOOLS))); //Maxdmg = Max + 2
        public static final RegistryObject<Item> TORCH_ROCKET = REGISTRY.register("torchrocket", () -> new Item(new Item.Properties().group(ItemGroup.TOOLS)));
        public static final RegistryObject<ItemTorchFirework> TORCH_FIREWORK = REGISTRY.register("torchfirework", () -> new ItemTorchFirework(new Item.Properties().group(ItemGroup.TOOLS)));
        public static final RegistryObject<ItemTorchLauncher> TORCH_LAUNCHER = REGISTRY.register("torchlauncher", () -> new ItemTorchLauncher(new Item.Properties().maxDamage(9).group(ItemGroup.TOOLS)));
    }

    public static class Particles
    {
        private static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);

        public static final RegistryObject<BasicParticleType> FLAME = REGISTRY.register("flame", () -> new BasicParticleType(true));
    }

    public static class Recipes
    {
        private static final DeferredRegister<IRecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);

        public static final RegistryObject<IRecipeSerializer<RecipeTorchGun>> TORCH_GUN_REPAIR = REGISTRY.register("torch_gun_repair", () -> new SpecialRecipeSerializer<>(RecipeTorchGun::new));
    }

    public static class Sounds
    {
        private static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID); //.setRegistryName(new ResourceLocation("torched", "rpt") ??

        public static final RegistryObject<SoundEvent> RPT = REGISTRY.register("rpt", () -> new SoundEvent(new ResourceLocation("torched", "rpt")));
        public static final RegistryObject<SoundEvent> TUBE = REGISTRY.register("tube", () -> new SoundEvent(new ResourceLocation("torched", "tube")));
    }
}
