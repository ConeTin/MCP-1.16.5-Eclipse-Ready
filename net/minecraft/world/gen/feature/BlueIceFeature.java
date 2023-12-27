package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class BlueIceFeature extends Feature<NoFeatureConfig> {
   public BlueIceFeature(Codec<NoFeatureConfig> p_i231933_1_) {
      super(p_i231933_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      if (pos.getY() > reader.getSeaLevel() - 1) {
         return false;
      } else if (!reader.getBlockState(pos).isIn(Blocks.WATER) && !reader.getBlockState(pos.down()).isIn(Blocks.WATER)) {
         return false;
      } else {
         boolean flag = false;

         for(Direction direction : Direction.values()) {
            if (direction != Direction.DOWN && reader.getBlockState(pos.offset(direction)).isIn(Blocks.PACKED_ICE)) {
               flag = true;
               break;
            }
         }

         if (!flag) {
            return false;
         } else {
            reader.setBlockState(pos, Blocks.BLUE_ICE.getDefaultState(), 2);

            for(int i = 0; i < 200; ++i) {
               int j = rand.nextInt(5) - rand.nextInt(6);
               int k = 3;
               if (j < 2) {
                  k += j / 2;
               }

               if (k >= 1) {
                  BlockPos blockpos = pos.add(rand.nextInt(k) - rand.nextInt(k), j, rand.nextInt(k) - rand.nextInt(k));
                  BlockState blockstate = reader.getBlockState(blockpos);
                  if (blockstate.getMaterial() == Material.AIR || blockstate.isIn(Blocks.WATER) || blockstate.isIn(Blocks.PACKED_ICE) || blockstate.isIn(Blocks.ICE)) {
                     for(Direction direction1 : Direction.values()) {
                        BlockState blockstate1 = reader.getBlockState(blockpos.offset(direction1));
                        if (blockstate1.isIn(Blocks.BLUE_ICE)) {
                           reader.setBlockState(blockpos, Blocks.BLUE_ICE.getDefaultState(), 2);
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
