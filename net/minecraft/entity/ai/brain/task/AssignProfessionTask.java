package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

public class AssignProfessionTask extends Task<VillagerEntity> {
   public AssignProfessionTask() {
      super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, VillagerEntity owner) {
      BlockPos blockpos = owner.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().getPos();
      return blockpos.withinDistance(owner.getPositionVec(), 2.0D) || owner.shouldAssignProfessionOnSpawn();
   }

   protected void startExecuting(ServerWorld worldIn, VillagerEntity entityIn, long gameTimeIn) {
      GlobalPos globalpos = entityIn.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get();
      entityIn.getBrain().removeMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
      entityIn.getBrain().setMemory(MemoryModuleType.JOB_SITE, globalpos);
      worldIn.setEntityState(entityIn, (byte)14);
      if (entityIn.getVillagerData().getProfession() == VillagerProfession.NONE) {
         MinecraftServer minecraftserver = worldIn.getServer();
         Optional.ofNullable(minecraftserver.getWorld(globalpos.getDimension())).flatMap((p_241376_1_) -> {
            return p_241376_1_.getPointOfInterestManager().getType(globalpos.getPos());
         }).flatMap((p_220390_0_) -> {
            return Registry.VILLAGER_PROFESSION.stream().filter((p_220389_1_) -> {
               return p_220389_1_.getPointOfInterest() == p_220390_0_;
            }).findFirst();
         }).ifPresent((p_220388_2_) -> {
            entityIn.setVillagerData(entityIn.getVillagerData().withProfession(p_220388_2_));
            entityIn.resetBrain(worldIn);
         });
      }
   }
}
