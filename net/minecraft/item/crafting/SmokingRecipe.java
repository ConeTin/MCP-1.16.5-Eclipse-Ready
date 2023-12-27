package net.minecraft.item.crafting;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SmokingRecipe extends AbstractCookingRecipe {
   public SmokingRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookTime) {
      super(IRecipeType.SMOKING, id, group, ingredient, result, experience, cookTime);
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getIcon() {
      return new ItemStack(Blocks.SMOKER);
   }

   public IRecipeSerializer<?> getSerializer() {
      return IRecipeSerializer.SMOKING;
   }
}
