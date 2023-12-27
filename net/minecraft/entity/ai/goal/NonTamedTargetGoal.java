package net.minecraft.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

public class NonTamedTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   private final TameableEntity tameable;

   public NonTamedTargetGoal(TameableEntity tameableIn, Class<T> targetClassIn, boolean checkSight, @Nullable Predicate<LivingEntity> targetPredicate) {
      super(tameableIn, targetClassIn, 10, checkSight, false, targetPredicate);
      this.tameable = tameableIn;
   }

   public boolean shouldExecute() {
      return !this.tameable.isTamed() && super.shouldExecute();
   }

   public boolean shouldContinueExecuting() {
      return this.targetEntitySelector != null ? this.targetEntitySelector.canTarget(this.goalOwner, this.nearestTarget) : super.shouldContinueExecuting();
   }
}
