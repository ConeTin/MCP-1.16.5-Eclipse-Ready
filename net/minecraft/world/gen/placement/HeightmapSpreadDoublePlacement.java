package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class HeightmapSpreadDoublePlacement<DC extends IPlacementConfig> extends HeightmapBasedPlacement<DC> {
   public HeightmapSpreadDoublePlacement(Codec<DC> codec) {
      super(codec);
   }

   protected Heightmap.Type func_241858_a(DC config) {
      return Heightmap.Type.MOTION_BLOCKING;
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, DC config, BlockPos pos) {
      int i = pos.getX();
      int j = pos.getZ();
      int k = helper.func_242893_a(this.func_241858_a(config), i, j);
      return k == 0 ? Stream.of() : Stream.of(new BlockPos(i, rand.nextInt(k * 2), j));
   }
}
