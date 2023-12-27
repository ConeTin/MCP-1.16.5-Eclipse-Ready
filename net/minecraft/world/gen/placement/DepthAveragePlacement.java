package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class DepthAveragePlacement extends SimplePlacement<DepthAverageConfig> {
   public DepthAveragePlacement(Codec<DepthAverageConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, DepthAverageConfig config, BlockPos pos) {
      int i = config.baseline;
      int j = config.spread;
      int k = pos.getX();
      int l = pos.getZ();
      int i1 = random.nextInt(j) + random.nextInt(j) - j + i;
      return Stream.of(new BlockPos(k, i1, l));
   }
}
