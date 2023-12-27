package net.minecraft.item;

public class SimpleFoiledItem extends Item {
   public SimpleFoiledItem(Item.Properties builder) {
      super(builder);
   }

   public boolean hasEffect(ItemStack stack) {
      return true;
   }
}
