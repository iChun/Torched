package me.ichun.mods.torched.common.item;

import me.ichun.mods.torched.common.Torched;
import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemTorchFirework extends Item
{
    public ItemTorchFirework(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        ItemStack is = context.getItem();
        if(context.getFace() == Direction.UP)
        {
            World world = context.getWorld();
            if(world.getBlockState(context.getPos()).isSolidSide(world, context.getPos(), context.getFace()))
            {
                is.shrink(1);
                if(!world.isRemote)
                {
                    world.addEntity(new EntityTorchFirework(Torched.EntityTypes.TORCH_FIREWORK.get(), world).placed(context.getPos().getX(), context.getPos().getY(), context.getPos().getZ(), context.getPlayer() != null && context.getPlayer().abilities.isCreativeMode && context.getPlayer().isSneaking()));
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
