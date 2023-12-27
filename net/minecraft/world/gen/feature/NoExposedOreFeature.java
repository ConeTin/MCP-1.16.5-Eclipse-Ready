package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

public class NoExposedOreFeature extends Feature<OreFeatureConfig> {
   NoExposedOreFeature(Codec<OreFeatureConfig> p_i231974_1_) {
      super(p_i231974_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, OreFeatureConfig config) {
      int i = rand.nextInt(config.size + 1);
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(int j = 0; j < i; ++j) {
         this.func_236327_a_(blockpos$mutable, rand, pos, Math.min(j, 7));
         if (config.target.test(reader.getBlockState(blockpos$mutable), rand) && !this.func_236326_a_(reader, blockpos$mutable)) {
            reader.setBlockState(blockpos$mutable, config.state, 2);
         }
      }

      return true;
   }

   private void func_236327_a_(BlockPos.Mutable p_236327_1_, Random p_236327_2_, BlockPos p_236327_3_, int p_236327_4_) {
      int i = this.func_236328_a_(p_236327_2_, p_236327_4_);
      int j = this.func_236328_a_(p_236327_2_, p_236327_4_);
      int k = this.func_236328_a_(p_236327_2_, p_236327_4_);
      p_236327_1_.setAndOffset(p_236327_3_, i, j, k);
   }

   private int func_236328_a_(Random p_236328_1_, int p_236328_2_) {
      return Math.round((p_236328_1_.nextFloat() - p_236328_1_.nextFloat()) * (float)p_236328_2_);
   }

   private boolean func_236326_a_(IWorld p_236326_1_, BlockPos p_236326_2_) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(Direction direction : Direction.values()) {
         blockpos$mutable.setAndMove(p_236326_2_, direction);
         if (p_236326_1_.getBlockState(blockpos$mutable).isAir()) {
            return true;
         }
      }

      return false;
   }
}
