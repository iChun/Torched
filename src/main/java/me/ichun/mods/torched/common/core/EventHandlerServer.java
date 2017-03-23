package me.ichun.mods.torched.common.core;

import me.ichun.mods.ichunutil.common.core.util.EntityHelper;
import me.ichun.mods.ichunutil.common.item.ItemHandler;
import me.ichun.mods.torched.common.Torched;
import me.ichun.mods.torched.common.entity.EntityTorch;
import me.ichun.mods.torched.common.item.ItemTorchGun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventHandlerServer
{
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event)
    {
        if(event.getEntityPlayer().getActiveItemStack() != null && event.getEntityPlayer().getActiveItemStack().getItem() instanceof ItemTorchGun)
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
        {
            Iterator<Map.Entry<String, Integer>> ite = playerDelay.entrySet().iterator();
            while(ite.hasNext())
            {
                Map.Entry<String, Integer> e = ite.next();

                e.setValue(e.getValue() - 1);
                if(e.getValue() == 0)
                {
                    EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(e.getKey());
                    if(player != null)
                    {
                        shootTorch(player);
                    }
                    else
                    {
                        ite.remove();
                    }
                }
            }
        }
    }

    public void shootTorch(EntityPlayer player)
    {
        playerDelay.put(player.getName(), 5);
        ItemStack is = ItemHandler.getUsableDualHandedItem(player);
        if(player.capabilities.isCreativeMode || is != null && is.getItem() instanceof ItemTorchGun && is.getItemDamage() < is.getMaxDamage() && EntityHelper.consumeInventoryItem(player.inventory, Item.getItemFromBlock(Blocks.TORCH)))
        {
            if(!player.capabilities.isCreativeMode)
            {
                is.setItemDamage(is.getItemDamage() + 1);
                player.inventory.markDirty();
            }
            EntityHelper.playSoundAtEntity(player, Torched.soundTube, SoundCategory.PLAYERS, 0.5F, 0.85F + (player.getRNG().nextFloat() * 2F - 1F) * 0.075F);
            player.worldObj.spawnEntityInWorld(new EntityTorch(player.worldObj, player));
        }
    }

    public HashMap<String, Integer> playerDelay = new HashMap<>();
}
