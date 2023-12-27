package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class MultishotEnchantment extends Enchantment {
   public MultishotEnchantment(Enchantment.Rarity rarity, EquipmentSlotType... slots) {
      super(rarity, EnchantmentType.CROSSBOW, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return 20;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return 50;
   }

   public int getMaxLevel() {
      return 1;
   }

   public boolean canApplyTogether(Enchantment ench) {
      return super.canApplyTogether(ench) && ench != Enchantments.PIERCING;
   }
}
