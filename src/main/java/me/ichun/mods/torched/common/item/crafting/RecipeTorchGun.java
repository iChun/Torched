package me.ichun.mods.torched.common.item.crafting;

import me.ichun.mods.torched.common.Torched;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeTorchGun extends SpecialRecipe
{
    private ItemStack output = ItemStack.EMPTY;

    public RecipeTorchGun(ResourceLocation name)
    {
        super(name);
    }

    @Override
    public boolean matches(CraftingInventory var1, World var2)
    {
        output = ItemStack.EMPTY;

        ItemStack gun = null;
        ItemStack fs = null;

        for(int j = 0; j < var1.getSizeInventory(); ++j)
        {
            ItemStack var6 = var1.getStackInSlot(j);
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
            else if(var6.getItem() == Torched.Items.TORCH_GUN.get())
            {
                if(gun == null)
                {
                    gun = var6;
                    if(gun.getDamage() == 1)
                    {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
        }

        if(gun != null && fs != null)
        {
            int dmg = fs.getDamage();
            if(gun.getDamage() <= fs.getDamage())
            {
                return false;
            }
            if(dmg == 0)
            {
                dmg = 1;
            }
            output = new ItemStack(Torched.Items.TORCH_GUN.get());
            output.setDamage(dmg);
            return true;
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory var1)
    {
        return output.copy();
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return output;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return null;
    }
}
