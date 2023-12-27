package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class ChorusPlantFeature extends Feature<NoFeatureConfig> {
   public ChorusPlantFeature(Codec<NoFeatureConfig> p_i231936_1_) {
      super(p_i231936_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      if (reader.isAirBlock(pos) && reader.getBlockState(pos.down()).isIn(Blocks.END_STONE)) {
         ChorusFlowerBlock.generatePlant(reader, pos, rand, 8);
         return true;
      } else {
         return false;
      }
   }
}
