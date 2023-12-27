package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class FillLayerFeature extends Feature<FillLayerConfig> {
   public FillLayerFeature(Codec<FillLayerConfig> p_i231954_1_) {
      super(p_i231954_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, FillLayerConfig config) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = pos.getX() + i;
            int l = pos.getZ() + j;
            int i1 = config.height;
            blockpos$mutable.setPos(k, i1, l);
            if (reader.getBlockState(blockpos$mutable).isAir()) {
               reader.setBlockState(blockpos$mutable, config.state, 2);
            }
         }
      }

      return true;
   }
}
