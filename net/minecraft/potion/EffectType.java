package net.minecraft.potion;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum EffectType {
   BENEFICIAL(TextFormatting.BLUE),
   HARMFUL(TextFormatting.RED),
   NEUTRAL(TextFormatting.BLUE);

   private final TextFormatting color;

   private EffectType(TextFormatting color) {
      this.color = color;
   }

   @OnlyIn(Dist.CLIENT)
   public TextFormatting getColor() {
      return this.color;
   }
}
