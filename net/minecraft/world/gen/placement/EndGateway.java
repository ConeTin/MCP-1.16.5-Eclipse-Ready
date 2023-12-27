package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class EndGateway extends Placement<NoPlacementConfig> {
   public EndGateway(Codec<NoPlacementConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, NoPlacementConfig config, BlockPos pos) {
      if (rand.nextInt(700) == 0) {
         int i = rand.nextInt(16) + pos.getX();
         int j = rand.nextInt(16) + pos.getZ();
         int k = helper.func_242893_a(Heightmap.Type.MOTION_BLOCKING, i, j);
         if (k > 0) {
            int l = k + 3 + rand.nextInt(7);
            return Stream.of(new BlockPos(i, l, j));
         }
      }

      return Stream.empty();
   }
}
