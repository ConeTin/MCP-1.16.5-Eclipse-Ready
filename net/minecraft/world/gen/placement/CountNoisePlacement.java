package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class CountNoisePlacement extends Placement<NoiseDependant> {
   public CountNoisePlacement(Codec<NoiseDependant> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, NoiseDependant config, BlockPos pos) {
      double d0 = Biome.INFO_NOISE.noiseAt((double)pos.getX() / 200.0D, (double)pos.getZ() / 200.0D, false);
      int i = d0 < config.noiseLevel ? config.belowNoise : config.aboveNoise;
      return IntStream.range(0, i).mapToObj((p_242879_1_) -> {
         return pos;
      });
   }
}
