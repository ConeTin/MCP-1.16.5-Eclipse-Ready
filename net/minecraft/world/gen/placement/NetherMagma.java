package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class NetherMagma extends Placement<NoPlacementConfig> {
   public NetherMagma(Codec<NoPlacementConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, NoPlacementConfig config, BlockPos pos) {
      int i = helper.func_242895_b();
      int j = i - 5 + rand.nextInt(10);
      return Stream.of(new BlockPos(pos.getX(), j, pos.getZ()));
   }
}
