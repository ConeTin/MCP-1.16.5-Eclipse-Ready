package net.minecraft.entity.ai.goal;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CatSitOnBlockGoal extends MoveToBlockGoal {
   private final CatEntity cat;

   public CatSitOnBlockGoal(CatEntity cat, double speed) {
      super(cat, speed, 8);
      this.cat = cat;
   }

   public boolean shouldExecute() {
      return this.cat.isTamed() && !this.cat.isSitting() && super.shouldExecute();
   }

   public void startExecuting() {
      super.startExecuting();
      this.cat.setSleeping(false);
   }

   public void resetTask() {
      super.resetTask();
      this.cat.setSleeping(false);
   }

   public void tick() {
      super.tick();
      this.cat.setSleeping(this.getIsAboveDestination());
   }

   protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
      if (!worldIn.isAirBlock(pos.up())) {
         return false;
      } else {
         BlockState blockstate = worldIn.getBlockState(pos);
         if (blockstate.isIn(Blocks.CHEST)) {
            return ChestTileEntity.getPlayersUsing(worldIn, pos) < 1;
         } else {
            return blockstate.isIn(Blocks.FURNACE) && blockstate.get(FurnaceBlock.LIT) ? true : blockstate.isInAndMatches(BlockTags.BEDS, (p_234025_0_) -> {
               return p_234025_0_.<BedPart>func_235903_d_(BedBlock.PART).map((p_234026_0_) -> {
                  return p_234026_0_ != BedPart.HEAD;
               }).orElse(true);
            });
         }
      }
   }
}
