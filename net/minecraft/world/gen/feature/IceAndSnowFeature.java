package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class IceAndSnowFeature extends Feature<NoFeatureConfig> {
   public IceAndSnowFeature(Codec<NoFeatureConfig> p_i231993_1_) {
      super(p_i231993_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
      BlockPos.Mutable blockpos$mutable1 = new BlockPos.Mutable();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = pos.getX() + i;
            int l = pos.getZ() + j;
            int i1 = reader.getHeight(Heightmap.Type.MOTION_BLOCKING, k, l);
            blockpos$mutable.setPos(k, i1, l);
            blockpos$mutable1.setPos(blockpos$mutable).move(Direction.DOWN, 1);
            Biome biome = reader.getBiome(blockpos$mutable);
            if (biome.doesWaterFreeze(reader, blockpos$mutable1, false)) {
               reader.setBlockState(blockpos$mutable1, Blocks.ICE.getDefaultState(), 2);
            }

            if (biome.doesSnowGenerate(reader, blockpos$mutable)) {
               reader.setBlockState(blockpos$mutable, Blocks.SNOW.getDefaultState(), 2);
               BlockState blockstate = reader.getBlockState(blockpos$mutable1);
               if (blockstate.hasProperty(SnowyDirtBlock.SNOWY)) {
                  reader.setBlockState(blockpos$mutable1, blockstate.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
               }
            }
         }
      }

      return true;
   }
}
