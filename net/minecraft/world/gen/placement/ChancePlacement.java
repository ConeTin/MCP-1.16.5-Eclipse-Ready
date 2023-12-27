package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class ChancePlacement extends SimplePlacement<ChanceConfig> {
   public ChancePlacement(Codec<ChanceConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, ChanceConfig config, BlockPos pos) {
      return random.nextFloat() < 1.0F / (float)config.chance ? Stream.of(pos) : Stream.empty();
   }
}
