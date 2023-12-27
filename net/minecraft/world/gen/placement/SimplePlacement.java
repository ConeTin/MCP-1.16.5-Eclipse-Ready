package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public abstract class SimplePlacement<DC extends IPlacementConfig> extends Placement<DC> {
   public SimplePlacement(Codec<DC> codec) {
      super(codec);
   }

   public final Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, DC config, BlockPos pos) {
      return this.getPositions(rand, config, pos);
   }

   protected abstract Stream<BlockPos> getPositions(Random random, DC config, BlockPos pos);
}
