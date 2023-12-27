package net.minecraft.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;

public class UseItemGoal<T extends MobEntity> extends Goal {
   private final T user;
   private final ItemStack stack;
   private final Predicate<? super T> field_220768_c;
   private final SoundEvent field_220769_d;

   public UseItemGoal(T user, ItemStack stack, @Nullable SoundEvent p_i50319_3_, Predicate<? super T> p_i50319_4_) {
      this.user = user;
      this.stack = stack;
      this.field_220769_d = p_i50319_3_;
      this.field_220768_c = p_i50319_4_;
   }

   public boolean shouldExecute() {
      return this.field_220768_c.test(this.user);
   }

   public boolean shouldContinueExecuting() {
      return this.user.isHandActive();
   }

   public void startExecuting() {
      this.user.setItemStackToSlot(EquipmentSlotType.MAINHAND, this.stack.copy());
      this.user.setActiveHand(Hand.MAIN_HAND);
   }

   public void resetTask() {
      this.user.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
      if (this.field_220769_d != null) {
         this.user.playSound(this.field_220769_d, 1.0F, this.user.getRNG().nextFloat() * 0.2F + 0.9F);
      }

   }
}
