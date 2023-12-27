package net.minecraft.entity.ai.goal;

import net.minecraft.entity.monster.ZombieEntity;

public class ZombieAttackGoal extends MeleeAttackGoal {
   private final ZombieEntity zombie;
   private int raiseArmTicks;

   public ZombieAttackGoal(ZombieEntity zombieIn, double speedIn, boolean longMemoryIn) {
      super(zombieIn, speedIn, longMemoryIn);
      this.zombie = zombieIn;
   }

   public void startExecuting() {
      super.startExecuting();
      this.raiseArmTicks = 0;
   }

   public void resetTask() {
      super.resetTask();
      this.zombie.setAggroed(false);
   }

   public void tick() {
      super.tick();
      ++this.raiseArmTicks;
      if (this.raiseArmTicks >= 5 && this.func_234041_j_() < this.func_234042_k_() / 2) {
         this.zombie.setAggroed(true);
      } else {
         this.zombie.setAggroed(false);
      }

   }
}
