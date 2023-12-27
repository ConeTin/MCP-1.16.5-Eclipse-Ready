package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class LakeWater extends Placement<ChanceConfig> {
   public LakeWater(Codec<ChanceConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, ChanceConfig config, BlockPos pos) {
      if (rand.nextInt(config.chance) == 0) {
         int i = rand.nextInt(16) + pos.getX();
         int j = rand.nextInt(16) + pos.getZ();
         int k = rand.nextInt(helper.func_242891_a());
         return Stream.of(new BlockPos(i, k, j));
      } else {
         return Stream.empty();
      }
   }
}
