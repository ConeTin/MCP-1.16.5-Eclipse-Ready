package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class SeaGrassFeature extends Feature<ProbabilityConfig> {
   public SeaGrassFeature(Codec<ProbabilityConfig> p_i231988_1_) {
      super(p_i231988_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ProbabilityConfig config) {
      boolean flag = false;
      int i = rand.nextInt(8) - rand.nextInt(8);
      int j = rand.nextInt(8) - rand.nextInt(8);
      int k = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + i, pos.getZ() + j);
      BlockPos blockpos = new BlockPos(pos.getX() + i, k, pos.getZ() + j);
      if (reader.getBlockState(blockpos).isIn(Blocks.WATER)) {
         boolean flag1 = rand.nextDouble() < (double)config.probability;
         BlockState blockstate = flag1 ? Blocks.TALL_SEAGRASS.getDefaultState() : Blocks.SEAGRASS.getDefaultState();
         if (blockstate.isValidPosition(reader, blockpos)) {
            if (flag1) {
               BlockState blockstate1 = blockstate.with(TallSeaGrassBlock.HALF, DoubleBlockHalf.UPPER);
               BlockPos blockpos1 = blockpos.up();
               if (reader.getBlockState(blockpos1).isIn(Blocks.WATER)) {
                  reader.setBlockState(blockpos, blockstate, 2);
                  reader.setBlockState(blockpos1, blockstate1, 2);
               }
            } else {
               reader.setBlockState(blockpos, blockstate, 2);
            }

            flag = true;
         }
      }

      return flag;
   }
}
