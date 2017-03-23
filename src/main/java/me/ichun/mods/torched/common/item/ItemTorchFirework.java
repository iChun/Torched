package me.ichun.mods.torched.common.item;

import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
    public EnumActionResult onItemUse(ItemStack is, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(is.getItemDamage() == 0)
        {
            if(side == EnumFacing.UP)
            {
                if(world.isSideSolid(pos, side, false))
                {
                    is.stackSize--;
                    if(!world.isRemote)
                    {
                        world.spawnEntityInWorld(new EntityTorchFirework(world, pos.getX(), pos.getY(), pos.getZ(), player.capabilities.isCreativeMode && player.isSneaking()));
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
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList)
    {
        itemList.add(new ItemStack(this, 1, 0));
        itemList.add(new ItemStack(this, 1, 1));
    }
}
