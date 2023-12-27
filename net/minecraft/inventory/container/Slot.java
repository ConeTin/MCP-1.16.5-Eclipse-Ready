package net.minecraft.inventory.container;

import com.mojang.datafixers.util.Pair;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Slot {
   private final int slotIndex;
   public final IInventory inventory;
   public int slotNumber;
   public final int xPos;
   public final int yPos;

   public Slot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
      this.inventory = inventoryIn;
      this.slotIndex = index;
      this.xPos = xPosition;
      this.yPos = yPosition;
   }

   public void onSlotChange(ItemStack oldStackIn, ItemStack newStackIn) {
      int i = newStackIn.getCount() - oldStackIn.getCount();
      if (i > 0) {
         this.onCrafting(newStackIn, i);
      }

   }

   protected void onCrafting(ItemStack stack, int amount) {
   }

   protected void onSwapCraft(int numItemsCrafted) {
   }

   protected void onCrafting(ItemStack stack) {
   }

   public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
      this.onSlotChanged();
      return stack;
   }

   public boolean isItemValid(ItemStack stack) {
      return true;
   }

   public ItemStack getStack() {
      return this.inventory.getStackInSlot(this.slotIndex);
   }

   public boolean getHasStack() {
      return !this.getStack().isEmpty();
   }

   public void putStack(ItemStack stack) {
      this.inventory.setInventorySlotContents(this.slotIndex, stack);
      this.onSlotChanged();
   }

   public void onSlotChanged() {
      this.inventory.markDirty();
   }

   public int getSlotStackLimit() {
      return this.inventory.getInventoryStackLimit();
   }

   public int getItemStackLimit(ItemStack stack) {
      return this.getSlotStackLimit();
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public Pair<ResourceLocation, ResourceLocation> getBackground() {
      return null;
   }

   public ItemStack decrStackSize(int amount) {
      return this.inventory.decrStackSize(this.slotIndex, amount);
   }

   public boolean canTakeStack(PlayerEntity playerIn) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isEnabled() {
      return true;
   }
}
