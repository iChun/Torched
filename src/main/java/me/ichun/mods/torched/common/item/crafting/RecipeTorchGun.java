package me.ichun.mods.torched.common.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import me.ichun.mods.torched.common.Torched;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class RecipeTorchGun extends ShapelessRecipes
{
    private ItemStack output = ItemStack.EMPTY;

    public RecipeTorchGun(String group, ItemStack output, NonNullList<Ingredient> ingredients)
    {
        super(group, output, ingredients);
    }

    @Override
    public boolean matches(InventoryCrafting var1, World var2)
    {
        output = ItemStack.EMPTY;

        ItemStack gun = null;
        ItemStack fs = null;

        for (int var4 = 0; var4 < 3; ++var4)
        {
            for (int var5 = 0; var5 < 3; ++var5)
            {
                ItemStack var6 = var1.getStackInRowAndColumn(var5, var4);
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
                        if(gun.getItemDamage() == 1)
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
    public ItemStack getRecipeOutput()
    {
        return output;
    }

    public static class Factory implements IRecipeFactory
    {
        @Override
        public IRecipe parse(JsonContext context, JsonObject json)
        {
            String group = JsonUtils.getString(json, "group", "");

            NonNullList<Ingredient> ings = NonNullList.create();
            for(JsonElement ele : JsonUtils.getJsonArray(json, "ingredients"))
                ings.add(CraftingHelper.getIngredient(ele, context));

            if(ings.isEmpty())
                throw new JsonParseException("No ingredients for shapeless recipe");
            if(ings.size() > 9)
                throw new JsonParseException("Too many ingredients for shapeless recipe");

            ItemStack itemstack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
            return new RecipeTorchGun(group, itemstack, ings);
        }
    }
}
