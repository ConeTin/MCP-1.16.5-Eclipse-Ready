package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class DarkOakTreePlacement extends HeightmapBasedPlacement<NoPlacementConfig> {
   public DarkOakTreePlacement(Codec<NoPlacementConfig> codec) {
      super(codec);
   }

   protected Heightmap.Type func_241858_a(NoPlacementConfig config) {
      return Heightmap.Type.MOTION_BLOCKING;
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, NoPlacementConfig config, BlockPos pos) {
      return IntStream.range(0, 16).mapToObj((p_242881_5_) -> {
         int i = p_242881_5_ / 4;
         int j = p_242881_5_ % 4;
         int k = i * 4 + 1 + rand.nextInt(3) + pos.getX();
         int l = j * 4 + 1 + rand.nextInt(3) + pos.getZ();
         int i1 = helper.func_242893_a(this.func_241858_a(config), k, l);
         return new BlockPos(k, i1, l);
      });
   }
}
