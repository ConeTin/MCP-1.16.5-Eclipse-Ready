package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

public abstract class FlowersFeature<U extends IFeatureConfig> extends Feature<U> {
   public FlowersFeature(Codec<U> p_i231922_1_) {
      super(p_i231922_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, U config) {
      BlockState blockstate = this.getFlowerToPlace(rand, pos, config);
      int i = 0;

      for(int j = 0; j < this.getFlowerCount(config); ++j) {
         BlockPos blockpos = this.getNearbyPos(rand, pos, config);
         if (reader.isAirBlock(blockpos) && blockpos.getY() < 255 && blockstate.isValidPosition(reader, blockpos) && this.isValidPosition(reader, blockpos, config)) {
            reader.setBlockState(blockpos, blockstate, 2);
            ++i;
         }
      }

      return i > 0;
   }

   public abstract boolean isValidPosition(IWorld world, BlockPos pos, U config);

   public abstract int getFlowerCount(U config);

   public abstract BlockPos getNearbyPos(Random rand, BlockPos pos, U config);

   public abstract BlockState getFlowerToPlace(Random rand, BlockPos pos, U confgi);
}
