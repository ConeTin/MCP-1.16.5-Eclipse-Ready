package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class SpringFeature extends Feature<LiquidsConfig> {
   public SpringFeature(Codec<LiquidsConfig> p_i231995_1_) {
      super(p_i231995_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, LiquidsConfig config) {
      if (!config.acceptedBlocks.contains(reader.getBlockState(pos.up()).getBlock())) {
         return false;
      } else if (config.needsBlockBelow && !config.acceptedBlocks.contains(reader.getBlockState(pos.down()).getBlock())) {
         return false;
      } else {
         BlockState blockstate = reader.getBlockState(pos);
         if (!blockstate.isAir() && !config.acceptedBlocks.contains(blockstate.getBlock())) {
            return false;
         } else {
            int i = 0;
            int j = 0;
            if (config.acceptedBlocks.contains(reader.getBlockState(pos.west()).getBlock())) {
               ++j;
            }

            if (config.acceptedBlocks.contains(reader.getBlockState(pos.east()).getBlock())) {
               ++j;
            }

            if (config.acceptedBlocks.contains(reader.getBlockState(pos.north()).getBlock())) {
               ++j;
            }

            if (config.acceptedBlocks.contains(reader.getBlockState(pos.south()).getBlock())) {
               ++j;
            }

            if (config.acceptedBlocks.contains(reader.getBlockState(pos.down()).getBlock())) {
               ++j;
            }

            int k = 0;
            if (reader.isAirBlock(pos.west())) {
               ++k;
            }

            if (reader.isAirBlock(pos.east())) {
               ++k;
            }

            if (reader.isAirBlock(pos.north())) {
               ++k;
            }

            if (reader.isAirBlock(pos.south())) {
               ++k;
            }

            if (reader.isAirBlock(pos.down())) {
               ++k;
            }

            if (j == config.rockAmount && k == config.holeAmount) {
               reader.setBlockState(pos, config.state.getBlockState(), 2);
               reader.getPendingFluidTicks().scheduleTick(pos, config.state.getFluid(), 0);
               ++i;
            }

            return i > 0;
         }
      }
   }
}
