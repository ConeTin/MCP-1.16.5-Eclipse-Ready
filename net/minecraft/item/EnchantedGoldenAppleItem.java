package net.minecraft.item;

public class EnchantedGoldenAppleItem extends Item {
   public EnchantedGoldenAppleItem(Item.Properties builder) {
      super(builder);
   }

   public boolean hasEffect(ItemStack stack) {
      return true;
   }
}
