package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class RandomPatchFeature extends Feature<BlockClusterFeatureConfig> {
   public RandomPatchFeature(Codec<BlockClusterFeatureConfig> p_i231979_1_) {
      super(p_i231979_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
      BlockState blockstate = config.stateProvider.getBlockState(rand, pos);
      BlockPos blockpos;
      if (config.field_227298_k_) {
         blockpos = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos);
      } else {
         blockpos = pos;
      }

      int i = 0;
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int j = 0; j < config.tryCount; ++j) {
         blockpos$mutable.setAndOffset(blockpos, rand.nextInt(config.xSpread + 1) - rand.nextInt(config.xSpread + 1), rand.nextInt(config.ySpread + 1) - rand.nextInt(config.ySpread + 1), rand.nextInt(config.zSpread + 1) - rand.nextInt(config.zSpread + 1));
         BlockPos blockpos1 = blockpos$mutable.down();
         BlockState blockstate1 = reader.getBlockState(blockpos1);
         if ((reader.isAirBlock(blockpos$mutable) || config.isReplaceable && reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) && blockstate.isValidPosition(reader, blockpos$mutable) && (config.whitelist.isEmpty() || config.whitelist.contains(blockstate1.getBlock())) && !config.blacklist.contains(blockstate1) && (!config.requiresWater || reader.getFluidState(blockpos1.west()).isTagged(FluidTags.WATER) || reader.getFluidState(blockpos1.east()).isTagged(FluidTags.WATER) || reader.getFluidState(blockpos1.north()).isTagged(FluidTags.WATER) || reader.getFluidState(blockpos1.south()).isTagged(FluidTags.WATER))) {
            config.blockPlacer.place(reader, blockpos$mutable, blockstate, rand);
            ++i;
         }
      }

      return i > 0;
   }
}
