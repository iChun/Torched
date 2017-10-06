package me.ichun.mods.torched.common.item;

import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemTorchFirework extends Item
{
    public ItemTorchFirework()
    {
        maxStackSize = 64;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        ItemStack is = player.getHeldItem(hand);
        if(is.getItemDamage() == 0)
        {
            if(side == EnumFacing.UP)
            {
                if(world.isSideSolid(pos, side, false))
                {
                    is.shrink(1);
                    if(!world.isRemote)
                    {
                        world.spawnEntity(new EntityTorchFirework(world, pos.getX(), pos.getY(), pos.getZ(), player.capabilities.isCreativeMode && player.isSneaking()));
                    }
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        if(itemstack.getItemDamage() == 0)
        {
            return "item.torched.torchrocket";
        }
        return "item.torched.torchfirework";
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
        }
    }
}
