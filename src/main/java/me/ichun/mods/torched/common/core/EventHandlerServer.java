package me.ichun.mods.torched.common.core;

import me.ichun.mods.ichunutil.common.entity.util.EntityHelper;
import me.ichun.mods.ichunutil.common.item.DualHandedItem;
import me.ichun.mods.torched.common.Torched;
import me.ichun.mods.torched.common.entity.EntityTorch;
import me.ichun.mods.torched.common.item.ItemTorchGun;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventHandlerServer
{
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event)
    {
        if(event.getPlayer().getActiveItemStack().getItem() instanceof ItemTorchGun)
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
                    PlayerEntity player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUsername(e.getKey());
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

    public void shootTorch(PlayerEntity player)
    {
        playerDelay.put(player.getName().getUnformattedComponentText(), 5);
        ItemStack is = DualHandedItem.getUsableDualHandedItem(player);
        if(player.abilities.isCreativeMode || is.getItem() instanceof ItemTorchGun && is.getDamage() < is.getMaxDamage() && EntityHelper.consumeInventoryItem(player.inventory, Blocks.TORCH.asItem()))
        {
            if(!player.abilities.isCreativeMode)
            {
                is.setDamage(is.getDamage() + 1);
                player.inventory.markDirty();
            }
            EntityHelper.playSoundAtEntity(player, Torched.Sounds.TUBE.get(), SoundCategory.PLAYERS, 0.5F, 0.85F + (player.getRNG().nextFloat() * 2F - 1F) * 0.075F);
            player.world.addEntity(new EntityTorch(Torched.EntityTypes.TORCH.get(), player.world).setShooter(player));
        }
    }

    public HashMap<String, Integer> playerDelay = new HashMap<>();
}
