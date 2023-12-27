package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class RangePlacement extends SimplePlacement<TopSolidRangeConfig> {
   public RangePlacement(Codec<TopSolidRangeConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, TopSolidRangeConfig config, BlockPos pos) {
      int i = pos.getX();
      int j = pos.getZ();
      int k = random.nextInt(config.maximum - config.topOffset) + config.bottomOffset;
      return Stream.of(new BlockPos(i, k, j));
   }
}
