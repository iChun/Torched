package me.ichun.mods.torched.client.core;

import me.ichun.mods.ichunutil.client.keybind.KeyEvent;
import me.ichun.mods.ichunutil.client.model.item.ModelBaseWrapper;
import me.ichun.mods.ichunutil.common.iChunUtil;
import me.ichun.mods.ichunutil.common.item.ItemHandler;
import me.ichun.mods.torched.client.render.ItemRenderTorchGun;
import me.ichun.mods.torched.client.render.ItemRenderTorchLauncher;
import me.ichun.mods.torched.common.Torched;
import me.ichun.mods.torched.common.item.ItemTorchGun;
import me.ichun.mods.torched.common.item.ItemTorchLauncher;
import me.ichun.mods.torched.common.packet.PacketKeyEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandlerClient
{
    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event)
    {
        event.getModelRegistry().putObject(new ModelResourceLocation("torched:torchlauncher", "inventory"), new ModelBaseWrapper(new ItemRenderTorchLauncher()).setItemDualHanded());
        event.getModelRegistry().putObject(new ModelResourceLocation("torched:torchgun", "inventory"), new ModelBaseWrapper(new ItemRenderTorchGun()).setItemDualHanded());
    }

    @SubscribeEvent
    public void onKeyEvent(KeyEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if(event.keyBind.keyIndex == mc.gameSettings.keyBindUseItem.getKeyCode() && event.keyBind.isMinecraftBind())
        {
            ItemStack currentInv = ItemHandler.getUsableDualHandedItem(mc.player);
            if(currentInv.getItem() instanceof ItemTorchGun)
            {
                if(event.keyBind.isPressed())
                {
                    if(mc.currentScreen == null && !iChunUtil.eventHandlerClient.hasScreen)
                    {
                        Torched.channel.sendToServer(new PacketKeyEvent(true));
                    }
                }
            }
            if(!event.keyBind.isPressed())
            {
                Torched.channel.sendToServer(new PacketKeyEvent(false));
            }
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.ClientTickEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if(event.phase == TickEvent.Phase.END && mc.world != null)
        {
            if(firing > 0)
            {
                firing--;
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.world != null)
        {
            if(event.phase == TickEvent.Phase.START)
            {
                ItemStack currentInv = ItemHandler.getUsableDualHandedItem(mc.player);
                boolean was = currentItemIsTorchGun;
                currentItemIsTorchGun = (currentInv.getItem() instanceof ItemTorchGun || currentInv.getItem() instanceof ItemTorchLauncher);
                if(was && !currentItemIsTorchGun)
                {
                    Torched.channel.sendToServer(new PacketKeyEvent(false));
                }
                if(prevCurItem != mc.player.inventory.currentItem)
                {
                    if(mc.player.inventory.currentItem >= 0 && mc.player.inventory.currentItem <= 9 && mc.entityRenderer.itemRenderer.equippedProgressMainHand >= 1.0F)
                    {
                        prevCurItem = mc.player.inventory.currentItem;
                    }
                    currentItemIsTorchGun = false;
                }
            }
        }
    }

    @SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event)
    {
        ModelLoader.setCustomModelResourceLocation(Torched.itemTorchGun, 0, new ModelResourceLocation("torched:torchgun", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Torched.itemTorchFirework, 0, new ModelResourceLocation("torched:torchfirework", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Torched.itemTorchFirework, 1, new ModelResourceLocation("torched:torchrpt", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Torched.itemTorchLauncher, 0, new ModelResourceLocation("torched:torchlauncher", "inventory"));

        ModelBakery.registerItemVariants(Torched.itemTorchFirework, new ResourceLocation("torched", "torchfirework"), new ResourceLocation("torched", "torchrpt"));
    }

    public int firing;

    private boolean currentItemIsTorchGun;
    private int prevCurItem;
}
