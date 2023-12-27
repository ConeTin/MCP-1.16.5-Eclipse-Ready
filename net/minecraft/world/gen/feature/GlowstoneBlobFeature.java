package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class GlowstoneBlobFeature extends Feature<NoFeatureConfig> {
   public GlowstoneBlobFeature(Codec<NoFeatureConfig> p_i231956_1_) {
      super(p_i231956_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      if (!reader.isAirBlock(pos)) {
         return false;
      } else {
         BlockState blockstate = reader.getBlockState(pos.up());
         if (!blockstate.isIn(Blocks.NETHERRACK) && !blockstate.isIn(Blocks.BASALT) && !blockstate.isIn(Blocks.BLACKSTONE)) {
            return false;
         } else {
            reader.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState(), 2);

            for(int i = 0; i < 1500; ++i) {
               BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));
               if (reader.getBlockState(blockpos).isAir()) {
                  int j = 0;

                  for(Direction direction : Direction.values()) {
                     if (reader.getBlockState(blockpos.offset(direction)).isIn(Blocks.GLOWSTONE)) {
                        ++j;
                     }

                     if (j > 1) {
                        break;
                     }
                  }

                  if (j == 1) {
                     reader.setBlockState(blockpos, Blocks.GLOWSTONE.getDefaultState(), 2);
                  }
               }
            }

            return true;
         }
      }
   }
}
