package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;

public class CountPlacement extends SimplePlacement<FeatureSpreadConfig> {
   public CountPlacement(Codec<FeatureSpreadConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, FeatureSpreadConfig config, BlockPos pos) {
      return IntStream.range(0, config.func_242799_a().func_242259_a(random)).mapToObj((p_242878_1_) -> {
         return pos;
      });
   }
}
