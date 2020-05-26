package me.ichun.mods.torched.client.core;

import me.ichun.mods.ichunutil.common.item.DualHandedItem;
import me.ichun.mods.torched.common.Torched;
import me.ichun.mods.torched.common.item.ItemTorchGun;
import me.ichun.mods.torched.common.item.ItemTorchLauncher;
import me.ichun.mods.torched.common.packet.PacketKeyEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandlerClient
{
    public boolean pressing = false;

    @SubscribeEvent
    public void onClickInput(InputEvent.ClickInputEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        ItemStack currentInv = DualHandedItem.getUsableDualHandedItem(mc.player);
        if(currentInv.getItem() instanceof ItemTorchGun && event.isUseItem())
        {
            event.setSwingHand(false);
            event.setCanceled(true);

            Torched.channel.sendToServer(new PacketKeyEvent(true));
            pressing = true;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if(event.phase == TickEvent.Phase.END)
        {
            if(mc.world != null)
            {
                if(firing > 0)
                {
                    firing--;
                }
            }
            if(pressing && !mc.gameSettings.keyBindUseItem.isKeyDown())
            {
                pressing = false;
                ItemStack currentInv = DualHandedItem.getUsableDualHandedItem(mc.player);
                if(currentInv.getItem() instanceof ItemTorchGun)
                {
                    Torched.channel.sendToServer(new PacketKeyEvent(false));
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null)
        {
            if(event.phase == TickEvent.Phase.START)
            {
                ItemStack currentInv = DualHandedItem.getUsableDualHandedItem(mc.player);
                boolean was = currentItemIsTorchGun;
                currentItemIsTorchGun = (currentInv.getItem() instanceof ItemTorchGun || currentInv.getItem() instanceof ItemTorchLauncher);
                if(was && !currentItemIsTorchGun)
                {
                    Torched.channel.sendToServer(new PacketKeyEvent(false));
                }
                if(prevCurItem != mc.player.inventory.currentItem)
                {
                    if(mc.player.inventory.currentItem >= 0 && mc.player.inventory.currentItem <= 9 && mc.getFirstPersonRenderer().equippedProgressMainHand >= 1.0F)
                    {
                        prevCurItem = mc.player.inventory.currentItem;
                    }
                    currentItemIsTorchGun = false;
                }
            }
        }
    }

    public void nudgeHand(PlayerEntity player)
    {
        if(player == Minecraft.getInstance().player)
        {
            ItemStack is = DualHandedItem.getUsableDualHandedItem(player);
            if(is.getItem() instanceof ItemTorchGun)
            {
                Minecraft.getInstance().player.renderArmPitch += 100F;
                Torched.eventHandlerClient.firing = 4;
            }
            else if(is.getItem() instanceof ItemTorchLauncher)
            {
                Minecraft.getInstance().player.renderArmPitch -= 200F;
            }
        }
    }

    public int firing;

    private boolean currentItemIsTorchGun;
    private int prevCurItem;
}
