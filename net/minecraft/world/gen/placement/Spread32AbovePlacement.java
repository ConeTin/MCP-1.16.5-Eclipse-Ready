package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class Spread32AbovePlacement extends Placement<NoPlacementConfig> {
   public Spread32AbovePlacement(Codec<NoPlacementConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, NoPlacementConfig config, BlockPos pos) {
      int i = rand.nextInt(pos.getY() + 32);
      return Stream.of(new BlockPos(pos.getX(), i, pos.getZ()));
   }
}
