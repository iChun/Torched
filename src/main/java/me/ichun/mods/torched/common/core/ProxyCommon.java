package me.ichun.mods.torched.common.core;

import me.ichun.mods.ichunutil.common.item.ItemHandler;
import me.ichun.mods.torched.common.Torched;
import me.ichun.mods.torched.common.entity.EntityTorch;
import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import me.ichun.mods.torched.common.item.ItemTorchGun;
import me.ichun.mods.torched.common.item.ItemTorchLauncher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ProxyCommon
{
    public void preInitMod()
    {
        EntityRegistry.registerModEntity(new ResourceLocation("torched", "entity_torch"), EntityTorch.class, "torchEnt", 20, Torched.instance, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("torched", "entity_torch_firework"), EntityTorchFirework.class, "torchFireworkEnt", 21, Torched.instance, 128, 2, true);

        Torched.eventHandlerServer = new EventHandlerServer();
        MinecraftForge.EVENT_BUS.register(Torched.eventHandlerServer);

        ItemHandler.registerDualHandedItem(ItemTorchGun.class);
        ItemHandler.registerDualHandedItem(ItemTorchLauncher.class);
    }

    public void nudgeHand(EntityPlayer player) {}

    public void spawnTorchFlame(Entity torch) {}
}
