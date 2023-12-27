package net.minecraft.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;

public class NearestAttackableTargetExpiringGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   private int cooldown = 0;

   public NearestAttackableTargetExpiringGoal(AbstractRaiderEntity raider, Class<T> targetClass, boolean checkSight, @Nullable Predicate<LivingEntity> p_i50311_4_) {
      super(raider, targetClass, 500, checkSight, false, p_i50311_4_);
   }

   public int getCooldown() {
      return this.cooldown;
   }

   public void tickCooldown() {
      --this.cooldown;
   }

   public boolean shouldExecute() {
      if (this.cooldown <= 0 && this.goalOwner.getRNG().nextBoolean()) {
         if (!((AbstractRaiderEntity)this.goalOwner).isRaidActive()) {
            return false;
         } else {
            this.findNearestTarget();
            return this.nearestTarget != null;
         }
      } else {
         return false;
      }
   }

   public void startExecuting() {
      this.cooldown = 200;
      super.startExecuting();
   }
}
