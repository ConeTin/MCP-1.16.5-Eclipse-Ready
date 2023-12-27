package net.minecraft.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlobReplacementConfig;
import net.minecraft.world.gen.feature.Feature;

public class NetherackBlobReplacementStructure extends Feature<BlobReplacementConfig> {
   public NetherackBlobReplacementStructure(Codec<BlobReplacementConfig> p_i231982_1_) {
      super(p_i231982_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlobReplacementConfig config) {
      Block block = config.field_242818_b.getBlock();
      BlockPos blockpos = func_236329_a_(reader, pos.toMutable().clampAxisCoordinate(Direction.Axis.Y, 1, reader.getHeight() - 1), block);
      if (blockpos == null) {
         return false;
      } else {
         int i = config.func_242823_b().func_242259_a(rand);
         boolean flag = false;

         for(BlockPos blockpos1 : BlockPos.getProximitySortedBoxPositionsIterator(blockpos, i, i, i)) {
            if (blockpos1.manhattanDistance(blockpos) > i) {
               break;
            }

            BlockState blockstate = reader.getBlockState(blockpos1);
            if (blockstate.isIn(block)) {
               this.setBlockState(reader, blockpos1, config.field_242819_c);
               flag = true;
            }
         }

         return flag;
      }
   }

   @Nullable
   private static BlockPos func_236329_a_(IWorld p_236329_0_, BlockPos.Mutable p_236329_1_, Block p_236329_2_) {
      while(p_236329_1_.getY() > 1) {
         BlockState blockstate = p_236329_0_.getBlockState(p_236329_1_);
         if (blockstate.isIn(p_236329_2_)) {
            return p_236329_1_;
         }

         p_236329_1_.move(Direction.DOWN);
      }

      return null;
   }
}
