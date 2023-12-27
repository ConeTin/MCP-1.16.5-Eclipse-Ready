package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class CountNoiseBiasedPlacement extends SimplePlacement<TopSolidWithNoiseConfig> {
   public CountNoiseBiasedPlacement(Codec<TopSolidWithNoiseConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, TopSolidWithNoiseConfig config, BlockPos pos) {
      double d0 = Biome.INFO_NOISE.noiseAt((double)pos.getX() / config.noiseFactor, (double)pos.getZ() / config.noiseFactor, false);
      int i = (int)Math.ceil((d0 + config.noiseOffset) * (double)config.noiseToCountRatio);
      return IntStream.range(0, i).mapToObj((p_242913_1_) -> {
         return pos;
      });
   }
}
