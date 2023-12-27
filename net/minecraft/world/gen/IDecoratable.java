package net.minecraft.world.gen;

import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public interface IDecoratable<R> {
   R withPlacement(ConfiguredPlacement<?> placement);

   default R chance(int p_242729_1_) {
      return this.withPlacement(Placement.CHANCE.configure(new ChanceConfig(p_242729_1_)));
   }

   default R func_242730_a(FeatureSpread p_242730_1_) {
      return this.withPlacement(Placement.COUNT.configure(new FeatureSpreadConfig(p_242730_1_)));
   }

   default R func_242731_b(int p_242731_1_) {
      return this.func_242730_a(FeatureSpread.func_242252_a(p_242731_1_));
   }

   default R func_242732_c(int p_242732_1_) {
      return this.func_242730_a(FeatureSpread.func_242253_a(0, p_242732_1_));
   }

   default R range(int p_242733_1_) {
      return this.withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, p_242733_1_)));
   }

   default R square() {
      return this.withPlacement(Placement.SQUARE.configure(NoPlacementConfig.INSTANCE));
   }
}
