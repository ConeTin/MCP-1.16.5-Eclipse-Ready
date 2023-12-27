package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.world.server.ServerWorld;

public class MateSensor extends Sensor<AgeableEntity> {
   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.VISIBLE_MOBS);
   }

   protected void update(ServerWorld worldIn, AgeableEntity entityIn) {
      entityIn.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).ifPresent((p_234118_2_) -> {
         this.addMemory(entityIn, p_234118_2_);
      });
   }

   private void addMemory(AgeableEntity entity, List<LivingEntity> entities) {
      Optional<AgeableEntity> optional = entities.stream().filter((p_234115_1_) -> {
         return p_234115_1_.getType() == entity.getType();
      }).map((p_234117_0_) -> {
         return (AgeableEntity)p_234117_0_;
      }).filter((p_234114_0_) -> {
         return !p_234114_0_.isChild();
      }).findFirst();
      entity.getBrain().setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT, optional);
   }
}
