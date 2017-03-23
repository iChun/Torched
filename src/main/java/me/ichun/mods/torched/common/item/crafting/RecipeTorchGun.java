package me.ichun.mods.torched.common.item.crafting;

import me.ichun.mods.torched.common.Torched;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeTorchGun 
	implements IRecipe 
{

	private ItemStack output = null;
	
	@Override
	public boolean matches(InventoryCrafting var1, World var2) 
	{
		output = null;
		
		ItemStack gun = null;
		ItemStack fs = null;
		
        for (int var4 = 0; var4 < 3; ++var4)
        {
            for (int var5 = 0; var5 < 3; ++var5)
            {
                ItemStack var6 = var1.getStackInRowAndColumn(var5, var4);
                
                if(var6 != null)
                {
                	if(var6.getItem() == Items.FLINT_AND_STEEL)
                	{
                		if(fs == null)
                		{
                			fs = var6;
                		}
                		else
                		{
                			return false;
                		}
                	}
                	else if(var6.getItem() == Torched.itemTorchGun)
                	{
                		if(gun == null)
                		{
                			gun = var6;
                		}
                		else
                		{
                			return false;
                		}
                	}
                }
            }
        }
        if(gun != null && fs != null)
        {
        	int dmg = fs.getItemDamage();
        	if(gun.getItemDamage() <= fs.getItemDamage())
        	{
        		return false;
        	}
        	if(dmg == 0)
        	{
        		dmg = 1;
        	}
        	output = new ItemStack(Torched.itemTorchGun, 1, dmg);
        	return true;
        }
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) 
	{
		return output.copy();
	}

	@Override
	public int getRecipeSize() 
	{
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() 
	{
		return output;
	}

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv)
    {
        ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
        }

        return aitemstack;
    }
}
