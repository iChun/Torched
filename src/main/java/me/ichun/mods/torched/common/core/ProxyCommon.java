package me.ichun.mods.torched.common.core;

import me.ichun.mods.ichunutil.common.item.ItemHandler;
import me.ichun.mods.torched.common.Torched;
import me.ichun.mods.torched.common.entity.EntityTorch;
import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import me.ichun.mods.torched.common.item.ItemTorchFirework;
import me.ichun.mods.torched.common.item.ItemTorchGun;
import me.ichun.mods.torched.common.item.ItemTorchLauncher;
import me.ichun.mods.torched.common.item.crafting.RecipeTorchGun;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

public class ProxyCommon
{
    public void preInitMod()
    {
        Torched.itemTorchGun = GameRegistry.register((new ItemTorchGun()).setFull3D().setRegistryName(new ResourceLocation("torched", "torchgun")).setUnlocalizedName("torched.torchgun").setCreativeTab(CreativeTabs.TOOLS));
        Torched.itemTorchFirework = GameRegistry.register((new ItemTorchFirework()).setRegistryName(new ResourceLocation("torched", "torchfirework")).setUnlocalizedName("torched.torchfirework").setCreativeTab(CreativeTabs.TOOLS));
        Torched.itemTorchLauncher = GameRegistry.register((new ItemTorchLauncher()).setRegistryName(new ResourceLocation("torched", "torchlauncher")).setFull3D().setUnlocalizedName("torched.torchlauncher").setCreativeTab(CreativeTabs.TOOLS));

        EntityRegistry.registerModEntity(EntityTorch.class, "torchEnt", 20, Torched.instance, 64, 20, true);
        EntityRegistry.registerModEntity(EntityTorchFirework.class, "torchFireworkEnt", 21, Torched.instance, 128, 2, true);

        Torched.eventHandlerServer = new EventHandlerServer();
        MinecraftForge.EVENT_BUS.register(Torched.eventHandlerServer);

        CraftingManager.getInstance().getRecipeList().add(new RecipeTorchGun());
        RecipeSorter.register("torched:torchgunRepair", RecipeTorchGun.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new ItemStack(Torched.itemTorchLauncher, 1),
                "LID", "IGI", "OIP", 'L', new ItemStack(Items.DYE, 1, 11), 'I', Items.IRON_INGOT, 'O', Blocks.OBSIDIAN, 'D', Blocks.DISPENSER, 'G', Items.GUNPOWDER, 'P', Blocks.PISTON);
        GameRegistry.addRecipe(new ItemStack(Torched.itemTorchFirework, 1, 1),
                "TTT", "TGT", "TTT", 'T', Blocks.TORCH, 'G', Items.GUNPOWDER);
        GameRegistry.addRecipe(new ItemStack(Torched.itemTorchFirework, 1, 0),
                "G", "T", 'T', Blocks.TORCH, 'G', Items.GUNPOWDER);
        GameRegistry.addRecipe(new ItemStack(Torched.itemTorchGun, 1, 65),
                "WI ", "IWI", "GIP", 'W', Blocks.PLANKS, 'I', Items.IRON_INGOT, 'G', Blocks.GLASS, 'P', Items.GUNPOWDER);

        Torched.soundRPT = GameRegistry.register(new SoundEvent(new ResourceLocation("torched", "rpt")).setRegistryName(new ResourceLocation("torched", "rpt")));
        Torched.soundTube = GameRegistry.register(new SoundEvent(new ResourceLocation("torched", "tube")).setRegistryName(new ResourceLocation("torched", "tube")));

        ItemHandler.registerDualHandedItem(ItemTorchGun.class);
        ItemHandler.registerDualHandedItem(ItemTorchLauncher.class);
    }

    public void nudgeHand(EntityPlayer player) {} //TODO test subtitles.

    public void spawnTorchFlame(Entity torch) {}
}
