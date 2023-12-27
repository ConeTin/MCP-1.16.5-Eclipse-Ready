package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class DecoratedPlacement extends Placement<DecoratedPlacementConfig> {
   public DecoratedPlacement(Codec<DecoratedPlacementConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, DecoratedPlacementConfig config, BlockPos pos) {
      return config.getOuter().func_242876_a(helper, rand, pos).flatMap((p_242882_3_) -> {
         return config.getInner().func_242876_a(helper, rand, p_242882_3_);
      });
   }
}
