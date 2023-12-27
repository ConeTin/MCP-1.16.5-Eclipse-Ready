package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class SoulSpeedEnchantment extends Enchantment {
   public SoulSpeedEnchantment(Enchantment.Rarity rarity, EquipmentSlotType... slots) {
      super(rarity, EnchantmentType.ARMOR_FEET, slots);
   }

   public int getMinEnchantability(int enchantmentLevel) {
      return enchantmentLevel * 10;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 15;
   }

   public boolean isTreasureEnchantment() {
      return true;
   }

   public boolean canVillagerTrade() {
      return false;
   }

   public boolean canGenerateInLoot() {
      return false;
   }

   public int getMaxLevel() {
      return 3;
   }
}
