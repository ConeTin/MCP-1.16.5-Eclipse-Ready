package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public class CaveEdge extends Placement<CaveEdgeConfig> {
   public CaveEdge(Codec<CaveEdgeConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, CaveEdgeConfig config, BlockPos pos) {
      ChunkPos chunkpos = new ChunkPos(pos);
      BitSet bitset = helper.func_242892_a(chunkpos, config.step);
      return IntStream.range(0, bitset.length()).filter((p_215067_3_) -> {
         return bitset.get(p_215067_3_) && rand.nextFloat() < config.probability;
      }).mapToObj((p_215068_1_) -> {
         int i = p_215068_1_ & 15;
         int j = p_215068_1_ >> 4 & 15;
         int k = p_215068_1_ >> 8;
         return new BlockPos(chunkpos.getXStart() + i, k, chunkpos.getZStart() + j);
      });
   }
}
