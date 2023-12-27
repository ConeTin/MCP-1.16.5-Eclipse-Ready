package net.minecraft.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

public class Inventory implements IInventory, IRecipeHelperPopulator {
   private final int slotsCount;
   private final NonNullList<ItemStack> inventoryContents;
   private List<IInventoryChangedListener> listeners;

   public Inventory(int numSlots) {
      this.slotsCount = numSlots;
      this.inventoryContents = NonNullList.withSize(numSlots, ItemStack.EMPTY);
   }

   public Inventory(ItemStack... stacksIn) {
      this.slotsCount = stacksIn.length;
      this.inventoryContents = NonNullList.from(ItemStack.EMPTY, stacksIn);
   }

   public void addListener(IInventoryChangedListener listener) {
      if (this.listeners == null) {
         this.listeners = Lists.newArrayList();
      }

      this.listeners.add(listener);
   }

   public void removeListener(IInventoryChangedListener listener) {
      this.listeners.remove(listener);
   }

   public ItemStack getStackInSlot(int index) {
      return index >= 0 && index < this.inventoryContents.size() ? this.inventoryContents.get(index) : ItemStack.EMPTY;
   }

   public List<ItemStack> func_233543_f_() {
      List<ItemStack> list = this.inventoryContents.stream().filter((p_233544_0_) -> {
         return !p_233544_0_.isEmpty();
      }).collect(Collectors.toList());
      this.clear();
      return list;
   }

   public ItemStack decrStackSize(int index, int count) {
      ItemStack itemstack = ItemStackHelper.getAndSplit(this.inventoryContents, index, count);
      if (!itemstack.isEmpty()) {
         this.markDirty();
      }

      return itemstack;
   }

   public ItemStack func_223374_a(Item p_223374_1_, int p_223374_2_) {
      ItemStack itemstack = new ItemStack(p_223374_1_, 0);

      for(int i = this.slotsCount - 1; i >= 0; --i) {
         ItemStack itemstack1 = this.getStackInSlot(i);
         if (itemstack1.getItem().equals(p_223374_1_)) {
            int j = p_223374_2_ - itemstack.getCount();
            ItemStack itemstack2 = itemstack1.split(j);
            itemstack.grow(itemstack2.getCount());
            if (itemstack.getCount() == p_223374_2_) {
               break;
            }
         }
      }

      if (!itemstack.isEmpty()) {
         this.markDirty();
      }

      return itemstack;
   }

   public ItemStack addItem(ItemStack stack) {
      ItemStack itemstack = stack.copy();
      this.func_223372_c(itemstack);
      if (itemstack.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         this.func_223375_b(itemstack);
         return itemstack.isEmpty() ? ItemStack.EMPTY : itemstack;
      }
   }

   public boolean func_233541_b_(ItemStack p_233541_1_) {
      boolean flag = false;

      for(ItemStack itemstack : this.inventoryContents) {
         if (itemstack.isEmpty() || this.func_233540_a_(itemstack, p_233541_1_) && itemstack.getCount() < itemstack.getMaxStackSize()) {
            flag = true;
            break;
         }
      }

      return flag;
   }

   public ItemStack removeStackFromSlot(int index) {
      ItemStack itemstack = this.inventoryContents.get(index);
      if (itemstack.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         this.inventoryContents.set(index, ItemStack.EMPTY);
         return itemstack;
      }
   }

   public void setInventorySlotContents(int index, ItemStack stack) {
      this.inventoryContents.set(index, stack);
      if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
         stack.setCount(this.getInventoryStackLimit());
      }

      this.markDirty();
   }

   public int getSizeInventory() {
      return this.slotsCount;
   }

   public boolean isEmpty() {
      for(ItemStack itemstack : this.inventoryContents) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public void markDirty() {
      if (this.listeners != null) {
         for(IInventoryChangedListener iinventorychangedlistener : this.listeners) {
            iinventorychangedlistener.onInventoryChanged(this);
         }
      }

   }

   public boolean isUsableByPlayer(PlayerEntity player) {
      return true;
   }

   public void clear() {
      this.inventoryContents.clear();
      this.markDirty();
   }

   public void fillStackedContents(RecipeItemHelper helper) {
      for(ItemStack itemstack : this.inventoryContents) {
         helper.accountStack(itemstack);
      }

   }

   public String toString() {
      return this.inventoryContents.stream().filter((p_223371_0_) -> {
         return !p_223371_0_.isEmpty();
      }).collect(Collectors.toList()).toString();
   }

   private void func_223375_b(ItemStack p_223375_1_) {
      for(int i = 0; i < this.slotsCount; ++i) {
         ItemStack itemstack = this.getStackInSlot(i);
         if (itemstack.isEmpty()) {
            this.setInventorySlotContents(i, p_223375_1_.copy());
            p_223375_1_.setCount(0);
            return;
         }
      }

   }

   private void func_223372_c(ItemStack p_223372_1_) {
      for(int i = 0; i < this.slotsCount; ++i) {
         ItemStack itemstack = this.getStackInSlot(i);
         if (this.func_233540_a_(itemstack, p_223372_1_)) {
            this.func_223373_a(p_223372_1_, itemstack);
            if (p_223372_1_.isEmpty()) {
               return;
            }
         }
      }

   }

   private boolean func_233540_a_(ItemStack p_233540_1_, ItemStack p_233540_2_) {
      return p_233540_1_.getItem() == p_233540_2_.getItem() && ItemStack.areItemStackTagsEqual(p_233540_1_, p_233540_2_);
   }

   private void func_223373_a(ItemStack p_223373_1_, ItemStack p_223373_2_) {
      int i = Math.min(this.getInventoryStackLimit(), p_223373_2_.getMaxStackSize());
      int j = Math.min(p_223373_1_.getCount(), i - p_223373_2_.getCount());
      if (j > 0) {
         p_223373_2_.grow(j);
         p_223373_1_.shrink(j);
         this.markDirty();
      }

   }

   public void read(ListNBT p_70486_1_) {
      for(int i = 0; i < p_70486_1_.size(); ++i) {
         ItemStack itemstack = ItemStack.read(p_70486_1_.getCompound(i));
         if (!itemstack.isEmpty()) {
            this.addItem(itemstack);
         }
      }

   }

   public ListNBT write() {
      ListNBT listnbt = new ListNBT();

      for(int i = 0; i < this.getSizeInventory(); ++i) {
         ItemStack itemstack = this.getStackInSlot(i);
         if (!itemstack.isEmpty()) {
            listnbt.add(itemstack.write(new CompoundNBT()));
         }
      }

      return listnbt;
   }
}
