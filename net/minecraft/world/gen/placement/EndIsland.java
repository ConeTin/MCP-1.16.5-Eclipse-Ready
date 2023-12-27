package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class EndIsland extends SimplePlacement<NoPlacementConfig> {
   public EndIsland(Codec<NoPlacementConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos) {
      Stream<BlockPos> stream = Stream.empty();
      if (random.nextInt(14) == 0) {
         stream = Stream.concat(stream, Stream.of(pos.add(random.nextInt(16), 55 + random.nextInt(16), random.nextInt(16))));
         if (random.nextInt(4) == 0) {
            stream = Stream.concat(stream, Stream.of(pos.add(random.nextInt(16), 55 + random.nextInt(16), random.nextInt(16))));
         }

         return stream;
      } else {
         return Stream.empty();
      }
   }
}
