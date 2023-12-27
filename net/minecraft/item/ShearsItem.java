package net.minecraft.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShearsItem extends Item {
   public ShearsItem(Item.Properties builder) {
      super(builder);
   }

   public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      if (!worldIn.isRemote && !state.getBlock().isIn(BlockTags.FIRE)) {
         stack.damageItem(1, entityLiving, (p_220036_0_) -> {
            p_220036_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
         });
      }

      return !state.isIn(BlockTags.LEAVES) && !state.isIn(Blocks.COBWEB) && !state.isIn(Blocks.GRASS) && !state.isIn(Blocks.FERN) && !state.isIn(Blocks.DEAD_BUSH) && !state.isIn(Blocks.VINE) && !state.isIn(Blocks.TRIPWIRE) && !state.isIn(BlockTags.WOOL) ? super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving) : true;
   }

   public boolean canHarvestBlock(BlockState blockIn) {
      return blockIn.isIn(Blocks.COBWEB) || blockIn.isIn(Blocks.REDSTONE_WIRE) || blockIn.isIn(Blocks.TRIPWIRE);
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      if (!state.isIn(Blocks.COBWEB) && !state.isIn(BlockTags.LEAVES)) {
         return state.isIn(BlockTags.WOOL) ? 5.0F : super.getDestroySpeed(stack, state);
      } else {
         return 15.0F;
      }
   }
}
