package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class SeaPickleFeature extends Feature<FeatureSpreadConfig> {
   public SeaPickleFeature(Codec<FeatureSpreadConfig> p_i231987_1_) {
      super(p_i231987_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, FeatureSpreadConfig config) {
      int i = 0;
      int j = config.func_242799_a().func_242259_a(rand);

      for(int k = 0; k < j; ++k) {
         int l = rand.nextInt(8) - rand.nextInt(8);
         int i1 = rand.nextInt(8) - rand.nextInt(8);
         int j1 = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + l, pos.getZ() + i1);
         BlockPos blockpos = new BlockPos(pos.getX() + l, j1, pos.getZ() + i1);
         BlockState blockstate = Blocks.SEA_PICKLE.getDefaultState().with(SeaPickleBlock.PICKLES, Integer.valueOf(rand.nextInt(4) + 1));
         if (reader.getBlockState(blockpos).isIn(Blocks.WATER) && blockstate.isValidPosition(reader, blockpos)) {
            reader.setBlockState(blockpos, blockstate, 2);
            ++i;
         }
      }

      return i > 0;
   }
}
