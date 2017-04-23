package me.ichun.mods.torched.client.core;

import me.ichun.mods.ichunutil.common.iChunUtil;
import me.ichun.mods.ichunutil.common.item.ItemHandler;
import me.ichun.mods.torched.common.item.ItemTorchLauncher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import me.ichun.mods.torched.client.entity.ParticleTorchFlameFX;
import me.ichun.mods.torched.client.render.RenderTorch;
import me.ichun.mods.torched.client.render.RenderTorchFirework;
import me.ichun.mods.torched.common.Torched;
import me.ichun.mods.torched.common.core.ProxyCommon;
import me.ichun.mods.torched.common.entity.EntityTorch;
import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import me.ichun.mods.torched.common.item.ItemTorchGun;

public class ProxyClient extends ProxyCommon
{

    @Override
    public void preInitMod()
    {
        super.preInitMod();

        iChunUtil.proxy.registerMinecraftKeyBind(Minecraft.getMinecraft().gameSettings.keyBindUseItem);

        Torched.eventHandlerClient = new EventHandlerClient();
        MinecraftForge.EVENT_BUS.register(Torched.eventHandlerClient);

        RenderingRegistry.registerEntityRenderingHandler(EntityTorch.class, new RenderTorch.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityTorchFirework.class, new RenderTorchFirework.RenderFactory());

        ModelLoader.setCustomModelResourceLocation(Torched.itemTorchGun, 0, new ModelResourceLocation("torched:torchgun", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Torched.itemTorchFirework, 0, new ModelResourceLocation("torched:torchfirework", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Torched.itemTorchFirework, 1, new ModelResourceLocation("torched:torchrpt", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Torched.itemTorchLauncher, 0, new ModelResourceLocation("torched:torchlauncher", "inventory"));

        ModelBakery.registerItemVariants(Torched.itemTorchFirework, new ResourceLocation("torched", "torchfirework"), new ResourceLocation("torched", "torchrpt"));
    }

    @Override
    public void nudgeHand(EntityPlayer player)
    {
        if(player == Minecraft.getMinecraft().thePlayer)
        {
            ItemStack is = ItemHandler.getUsableDualHandedItem(player);
            if(is != null)
            {
                if(is.getItem() instanceof ItemTorchGun)
                {
                    Minecraft.getMinecraft().thePlayer.renderArmPitch += 100F;
                    Torched.eventHandlerClient.firing = 4;
                }
                else if(is.getItem() instanceof ItemTorchLauncher)
                {
                    Minecraft.getMinecraft().thePlayer.renderArmPitch -= 200F;
                }
            }
        }
    }

    @Override
    public void spawnTorchFlame(Entity torch)
    {
        double speed = 0.75D;
        Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTorchFlameFX(torch.worldObj, torch.posX - torch.motionX * 0.3D, torch.posY - torch.motionY * 0.3D, torch.posZ - torch.motionZ * 0.3D, torch.motionX * speed, torch.motionY * speed, torch.motionZ * speed, torch instanceof EntityTorchFirework ? (1.0F + (((EntityTorchFirework)torch).getRocket() ? 1.0F : (float)((EntityTorchFirework)torch).getTorches() / 96F)) : 1.0F));
        //		Minecraft.getMinecraft().effectRenderer.addEffect(new EntityTorchFlameFX(torch.worldObj, torch.posX + torch.motionX * 0.5D, torch.posY + torch.motionY * 0.5D, torch.posZ + torch.motionZ * 0.5D, torch.motionX * speed, torch.motionY * speed, torch.motionZ * speed));
    }

}
