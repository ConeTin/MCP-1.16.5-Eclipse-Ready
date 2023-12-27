package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractBodyPlantBlock extends AbstractPlantBlock implements IGrowable {
   protected AbstractBodyPlantBlock(AbstractBlock.Properties properties, Direction growthDirection, VoxelShape shape, boolean waterloggable) {
      super(properties, growthDirection, shape, waterloggable);
   }

   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (facing == this.growthDirection.getOpposite() && !stateIn.isValidPosition(worldIn, currentPos)) {
         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
      }

      AbstractTopPlantBlock abstracttopplantblock = this.getTopPlantBlock();
      if (facing == this.growthDirection) {
         Block block = facingState.getBlock();
         if (block != this && block != abstracttopplantblock) {
            return abstracttopplantblock.grow(worldIn);
         }
      }

      if (this.breaksInWater) {
         worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
      }

      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
      return new ItemStack(this.getTopPlantBlock());
   }

   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      Optional<BlockPos> optional = this.nextGrowPosition(worldIn, pos, state);
      return optional.isPresent() && this.getTopPlantBlock().canGrowIn(worldIn.getBlockState(optional.get().offset(this.growthDirection)));
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
      Optional<BlockPos> optional = this.nextGrowPosition(worldIn, pos, state);
      if (optional.isPresent()) {
         BlockState blockstate = worldIn.getBlockState(optional.get());
         ((AbstractTopPlantBlock)blockstate.getBlock()).grow(worldIn, rand, optional.get(), blockstate);
      }

   }

   private Optional<BlockPos> nextGrowPosition(IBlockReader reader, BlockPos pos, BlockState state) {
      BlockPos blockpos = pos;

      Block block;
      do {
         blockpos = blockpos.offset(this.growthDirection);
         block = reader.getBlockState(blockpos).getBlock();
      } while(block == state.getBlock());

      return block == this.getTopPlantBlock() ? Optional.of(blockpos) : Optional.empty();
   }

   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      boolean flag = super.isReplaceable(state, useContext);
      return flag && useContext.getItem().getItem() == this.getTopPlantBlock().asItem() ? false : flag;
   }

   protected Block getBodyPlantBlock() {
      return this;
   }
}
