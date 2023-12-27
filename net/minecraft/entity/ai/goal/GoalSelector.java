package net.minecraft.entity.ai.goal;

import com.google.common.collect.Sets;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.profiler.IProfiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoalSelector {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final PrioritizedGoal DUMMY = new PrioritizedGoal(Integer.MAX_VALUE, new Goal() {
      public boolean shouldExecute() {
         return false;
      }
   }) {
      public boolean isRunning() {
         return false;
      }
   };
   private final Map<Goal.Flag, PrioritizedGoal> flagGoals = new EnumMap<>(Goal.Flag.class);
   private final Set<PrioritizedGoal> goals = Sets.newLinkedHashSet();
   private final Supplier<IProfiler> profiler;
   private final EnumSet<Goal.Flag> disabledFlags = EnumSet.noneOf(Goal.Flag.class);
   private int tickRate = 3;

   public GoalSelector(Supplier<IProfiler> profiler) {
      this.profiler = profiler;
   }

   public void addGoal(int priority, Goal task) {
      this.goals.add(new PrioritizedGoal(priority, task));
   }

   public void removeGoal(Goal task) {
      this.goals.stream().filter((p_220882_1_) -> {
         return p_220882_1_.getGoal() == task;
      }).filter(PrioritizedGoal::isRunning).forEach(PrioritizedGoal::resetTask);
      this.goals.removeIf((p_220884_1_) -> {
         return p_220884_1_.getGoal() == task;
      });
   }

   public void tick() {
      IProfiler iprofiler = this.profiler.get();
      iprofiler.startSection("goalCleanup");
      this.getRunningGoals().filter((p_220881_1_) -> {
         return !p_220881_1_.isRunning() || p_220881_1_.getMutexFlags().stream().anyMatch(this.disabledFlags::contains) || !p_220881_1_.shouldContinueExecuting();
      }).forEach(Goal::resetTask);
      this.flagGoals.forEach((p_220885_1_, p_220885_2_) -> {
         if (!p_220885_2_.isRunning()) {
            this.flagGoals.remove(p_220885_1_);
         }

      });
      iprofiler.endSection();
      iprofiler.startSection("goalUpdate");
      this.goals.stream().filter((p_220883_0_) -> {
         return !p_220883_0_.isRunning();
      }).filter((p_220879_1_) -> {
         return p_220879_1_.getMutexFlags().stream().noneMatch(this.disabledFlags::contains);
      }).filter((p_220889_1_) -> {
         return p_220889_1_.getMutexFlags().stream().allMatch((p_220887_2_) -> {
            return this.flagGoals.getOrDefault(p_220887_2_, DUMMY).isPreemptedBy(p_220889_1_);
         });
      }).filter(PrioritizedGoal::shouldExecute).forEach((p_220877_1_) -> {
         p_220877_1_.getMutexFlags().forEach((p_220876_2_) -> {
            PrioritizedGoal prioritizedgoal = this.flagGoals.getOrDefault(p_220876_2_, DUMMY);
            prioritizedgoal.resetTask();
            this.flagGoals.put(p_220876_2_, p_220877_1_);
         });
         p_220877_1_.startExecuting();
      });
      iprofiler.endSection();
      iprofiler.startSection("goalTick");
      this.getRunningGoals().forEach(PrioritizedGoal::tick);
      iprofiler.endSection();
   }

   public Stream<PrioritizedGoal> getRunningGoals() {
      return this.goals.stream().filter(PrioritizedGoal::isRunning);
   }

   public void disableFlag(Goal.Flag flag) {
      this.disabledFlags.add(flag);
   }

   public void enableFlag(Goal.Flag flag) {
      this.disabledFlags.remove(flag);
   }

   public void setFlag(Goal.Flag flag, boolean p_220878_2_) {
      if (p_220878_2_) {
         this.enableFlag(flag);
      } else {
         this.disableFlag(flag);
      }

   }
}
