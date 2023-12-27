package net.minecraft.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public abstract class ProjectileDispenseBehavior extends DefaultDispenseItemBehavior {
   public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
      World world = source.getWorld();
      IPosition iposition = DispenserBlock.getDispensePosition(source);
      Direction direction = source.getBlockState().get(DispenserBlock.FACING);
      ProjectileEntity projectileentity = this.getProjectileEntity(world, iposition, stack);
      projectileentity.shoot((double)direction.getXOffset(), (double)((float)direction.getYOffset() + 0.1F), (double)direction.getZOffset(), this.getProjectileVelocity(), this.getProjectileInaccuracy());
      world.addEntity(projectileentity);
      stack.shrink(1);
      return stack;
   }

   protected void playDispenseSound(IBlockSource source) {
      source.getWorld().playEvent(1002, source.getBlockPos(), 0);
   }

   protected abstract ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn);

   protected float getProjectileInaccuracy() {
      return 6.0F;
   }

   protected float getProjectileVelocity() {
      return 1.1F;
   }
}
