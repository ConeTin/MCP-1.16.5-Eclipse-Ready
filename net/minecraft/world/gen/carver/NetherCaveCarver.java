package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NetherCaveCarver extends CaveWorldCarver {
   public NetherCaveCarver(Codec<ProbabilityConfig> p_i231918_1_) {
      super(p_i231918_1_, 128);
      this.carvableBlocks = ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.SOUL_SOIL, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.BASALT, Blocks.BLACKSTONE);
      this.carvableFluids = ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
   }

   protected int func_230357_a_() {
      return 10;
   }

   protected float func_230359_a_(Random p_230359_1_) {
      return (p_230359_1_.nextFloat() * 2.0F + p_230359_1_.nextFloat()) * 2.0F;
   }

   protected double func_230360_b_() {
      return 5.0D;
   }

   protected int func_230361_b_(Random p_230361_1_) {
      return p_230361_1_.nextInt(this.maxHeight);
   }

   protected boolean carveBlock(IChunk chunk, Function<BlockPos, Biome> p_230358_2_, BitSet carvingMask, Random rand, BlockPos.Mutable p_230358_5_, BlockPos.Mutable p_230358_6_, BlockPos.Mutable p_230358_7_, int p_230358_8_, int p_230358_9_, int p_230358_10_, int posX, int posZ, int p_230358_13_, int posY, int p_230358_15_, MutableBoolean isSurface) {
      int i = p_230358_13_ | p_230358_15_ << 4 | posY << 8;
      if (carvingMask.get(i)) {
         return false;
      } else {
         carvingMask.set(i);
         p_230358_5_.setPos(posX, posY, posZ);
         if (this.isCarvable(chunk.getBlockState(p_230358_5_))) {
            BlockState blockstate;
            if (posY <= 31) {
               blockstate = LAVA.getBlockState();
            } else {
               blockstate = CAVE_AIR;
            }

            chunk.setBlockState(p_230358_5_, blockstate, false);
            return true;
         } else {
            return false;
         }
      }
   }
}
