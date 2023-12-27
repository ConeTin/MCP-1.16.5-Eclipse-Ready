package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class Passthrough extends SimplePlacement<NoPlacementConfig> {
   public Passthrough(Codec<NoPlacementConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos) {
      return Stream.of(pos);
   }
}
