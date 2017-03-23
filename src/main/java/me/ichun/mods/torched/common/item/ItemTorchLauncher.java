package me.ichun.mods.torched.common.item;

import me.ichun.mods.ichunutil.common.core.util.EntityHelper;
import me.ichun.mods.ichunutil.common.item.ItemHandler;
import me.ichun.mods.torched.common.Torched;
import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;


public class ItemTorchLauncher extends Item
{

    public ItemTorchLauncher()
    {
        maxStackSize = 1;
        setMaxDamage(9);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack is, World world, EntityPlayer player, EnumHand hand)
    {
//        player.setActiveHand(hand);
        if(ItemHandler.getUsableDualHandedItem(player) == is && (player.capabilities.isCreativeMode || EntityHelper.consumeInventoryItem(player.inventory, Torched.itemTorchFirework, 1, 1)))
        {
            Torched.proxy.nudgeHand(player);
            if(!world.isRemote)
            {
                EntityHelper.playSoundAtEntity(player, Torched.soundRPT, SoundCategory.PLAYERS, 1.0F, 0.9F + (player.getRNG().nextFloat() * 2F - 1F) * 0.075F);
                player.worldObj.spawnEntityInWorld(new EntityTorchFirework(player.worldObj, player));
            }
        }
        return new ActionResult(EnumActionResult.PASS, is);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList)
    {
        itemList.add(new ItemStack(this, 1));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
    {
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged || !ItemStack.areItemStacksEqual(oldStack, newStack);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return Integer.MAX_VALUE;
    }
}
