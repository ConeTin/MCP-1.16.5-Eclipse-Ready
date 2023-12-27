package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class CountExtraPlacement extends SimplePlacement<AtSurfaceWithExtraConfig> {
   public CountExtraPlacement(Codec<AtSurfaceWithExtraConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, AtSurfaceWithExtraConfig config, BlockPos pos) {
      int i = config.count + (random.nextFloat() < config.extraChance ? config.extraCount : 0);
      return IntStream.range(0, i).mapToObj((p_242880_1_) -> {
         return pos;
      });
   }
}
