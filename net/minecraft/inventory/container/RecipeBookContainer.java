package net.minecraft.inventory.container;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.ServerRecipePlacer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class RecipeBookContainer<C extends IInventory> extends Container {
   public RecipeBookContainer(ContainerType<?> type, int id) {
      super(type, id);
   }

   public void func_217056_a(boolean p_217056_1_, IRecipe<?> p_217056_2_, ServerPlayerEntity player) {
      (new ServerRecipePlacer<>(this)).place(player, (IRecipe<C>)p_217056_2_, p_217056_1_);
   }

   public abstract void fillStackedContents(RecipeItemHelper itemHelperIn);

   public abstract void clear();

   public abstract boolean matches(IRecipe<? super C> recipeIn);

   public abstract int getOutputSlot();

   public abstract int getWidth();

   public abstract int getHeight();

   @OnlyIn(Dist.CLIENT)
   public abstract int getSize();

   @OnlyIn(Dist.CLIENT)
   public abstract RecipeBookCategory func_241850_m();
}
