package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class LakeLava extends Placement<ChanceConfig> {
   public LakeLava(Codec<ChanceConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, ChanceConfig config, BlockPos pos) {
      if (rand.nextInt(config.chance / 10) == 0) {
         int i = rand.nextInt(16) + pos.getX();
         int j = rand.nextInt(16) + pos.getZ();
         int k = rand.nextInt(rand.nextInt(helper.func_242891_a() - 8) + 8);
         if (k < helper.func_242895_b() || rand.nextInt(config.chance / 8) == 0) {
            return Stream.of(new BlockPos(i, k, j));
         }
      }

      return Stream.empty();
   }
}
