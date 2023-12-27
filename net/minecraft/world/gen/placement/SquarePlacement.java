package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class SquarePlacement extends SimplePlacement<NoPlacementConfig> {
   public SquarePlacement(Codec<NoPlacementConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos) {
      int i = random.nextInt(16) + pos.getX();
      int j = random.nextInt(16) + pos.getZ();
      int k = pos.getY();
      return Stream.of(new BlockPos(i, k, j));
   }
}
