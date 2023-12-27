package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BambooLeaves;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class BambooFeature extends Feature<ProbabilityConfig> {
   private static final BlockState BAMBOO_BASE = Blocks.BAMBOO.getDefaultState().with(BambooBlock.PROPERTY_AGE, Integer.valueOf(1)).with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.NONE).with(BambooBlock.PROPERTY_STAGE, Integer.valueOf(0));
   private static final BlockState BAMBOO_LARGE_LEAVES_GROWN = BAMBOO_BASE.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.LARGE).with(BambooBlock.PROPERTY_STAGE, Integer.valueOf(1));
   private static final BlockState BAMBOO_LARGE_LEAVES = BAMBOO_BASE.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.LARGE);
   private static final BlockState BAMBOO_SMALL_LEAVES = BAMBOO_BASE.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.SMALL);

   public BambooFeature(Codec<ProbabilityConfig> codec) {
      super(codec);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ProbabilityConfig config) {
      int i = 0;
      BlockPos.Mutable blockpos$mutable = pos.toMutable();
      BlockPos.Mutable blockpos$mutable1 = pos.toMutable();
      if (reader.isAirBlock(blockpos$mutable)) {
         if (Blocks.BAMBOO.getDefaultState().isValidPosition(reader, blockpos$mutable)) {
            int j = rand.nextInt(12) + 5;
            if (rand.nextFloat() < config.probability) {
               int k = rand.nextInt(4) + 1;

               for(int l = pos.getX() - k; l <= pos.getX() + k; ++l) {
                  for(int i1 = pos.getZ() - k; i1 <= pos.getZ() + k; ++i1) {
                     int j1 = l - pos.getX();
                     int k1 = i1 - pos.getZ();
                     if (j1 * j1 + k1 * k1 <= k * k) {
                        blockpos$mutable1.setPos(l, reader.getHeight(Heightmap.Type.WORLD_SURFACE, l, i1) - 1, i1);
                        if (isDirt(reader.getBlockState(blockpos$mutable1).getBlock())) {
                           reader.setBlockState(blockpos$mutable1, Blocks.PODZOL.getDefaultState(), 2);
                        }
                     }
                  }
               }
            }

            for(int l1 = 0; l1 < j && reader.isAirBlock(blockpos$mutable); ++l1) {
               reader.setBlockState(blockpos$mutable, BAMBOO_BASE, 2);
               blockpos$mutable.move(Direction.UP, 1);
            }

            if (blockpos$mutable.getY() - pos.getY() >= 3) {
               reader.setBlockState(blockpos$mutable, BAMBOO_LARGE_LEAVES_GROWN, 2);
               reader.setBlockState(blockpos$mutable.move(Direction.DOWN, 1), BAMBOO_LARGE_LEAVES, 2);
               reader.setBlockState(blockpos$mutable.move(Direction.DOWN, 1), BAMBOO_SMALL_LEAVES, 2);
            }
         }

         ++i;
      }

      return i > 0;
   }
}
