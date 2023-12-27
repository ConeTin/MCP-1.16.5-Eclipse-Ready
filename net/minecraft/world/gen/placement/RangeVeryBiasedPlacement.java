package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class RangeVeryBiasedPlacement extends SimplePlacement<TopSolidRangeConfig> {
   public RangeVeryBiasedPlacement(Codec<TopSolidRangeConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, TopSolidRangeConfig config, BlockPos pos) {
      int i = pos.getX();
      int j = pos.getZ();
      int k = random.nextInt(random.nextInt(random.nextInt(config.maximum - config.topOffset) + config.bottomOffset) + config.bottomOffset);
      return Stream.of(new BlockPos(i, k, j));
   }
}
