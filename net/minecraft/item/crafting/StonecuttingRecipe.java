package net.minecraft.item.crafting;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StonecuttingRecipe extends SingleItemRecipe {
   public StonecuttingRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result) {
      super(IRecipeType.STONECUTTING, IRecipeSerializer.STONECUTTING, id, group, ingredient, result);
   }

   public boolean matches(IInventory inv, World worldIn) {
      return this.ingredient.test(inv.getStackInSlot(0));
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getIcon() {
      return new ItemStack(Blocks.STONECUTTER);
   }
}
