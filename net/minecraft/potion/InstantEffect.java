package net.minecraft.potion;

public class InstantEffect extends Effect {
   public InstantEffect(EffectType type, int liquidColor) {
      super(type, liquidColor);
   }

   public boolean isInstant() {
      return true;
   }

   public boolean isReady(int duration, int amplifier) {
      return duration >= 1;
   }
}
