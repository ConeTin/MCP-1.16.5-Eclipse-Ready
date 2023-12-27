package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobSpawnInfo {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final MobSpawnInfo EMPTY = new MobSpawnInfo(0.1F, Stream.of(EntityClassification.values()).collect(ImmutableMap.toImmutableMap((p_242565_0_) -> {
      return p_242565_0_;
   }, (p_242563_0_) -> {
      return ImmutableList.of();
   })), ImmutableMap.of(), false);
   public static final MapCodec<MobSpawnInfo> CODEC = RecordCodecBuilder.mapCodec((p_242561_0_) -> {
      return p_242561_0_.group(Codec.FLOAT.optionalFieldOf("creature_spawn_probability", Float.valueOf(0.1F)).forGetter((p_242566_0_) -> {
         return p_242566_0_.creatureSpawnProbability;
      }), Codec.simpleMap(EntityClassification.CODEC, MobSpawnInfo.Spawners.CODEC.listOf().promotePartial(Util.func_240982_a_("Spawn data: ", LOGGER::error)), IStringSerializable.createKeyable(EntityClassification.values())).fieldOf("spawners").forGetter((p_242564_0_) -> {
         return p_242564_0_.spawners;
      }), Codec.simpleMap(Registry.ENTITY_TYPE, MobSpawnInfo.SpawnCosts.CODEC, Registry.ENTITY_TYPE).fieldOf("spawn_costs").forGetter((p_242560_0_) -> {
         return p_242560_0_.spawnCosts;
      }), Codec.BOOL.fieldOf("player_spawn_friendly").orElse(false).forGetter(MobSpawnInfo::isValidSpawnBiomeForPlayer)).apply(p_242561_0_, MobSpawnInfo::new);
   });
   private final float creatureSpawnProbability;
   private final Map<EntityClassification, List<MobSpawnInfo.Spawners>> spawners;
   private final Map<EntityType<?>, MobSpawnInfo.SpawnCosts> spawnCosts;
   private final boolean validSpawnBiomeForPlayer;

   private MobSpawnInfo(float creatureSpawnProbability, Map<EntityClassification, List<MobSpawnInfo.Spawners>> spawners, Map<EntityType<?>, MobSpawnInfo.SpawnCosts> spawnCosts, boolean isValidSpawnBiomeForPlayer) {
      this.creatureSpawnProbability = creatureSpawnProbability;
      this.spawners = spawners;
      this.spawnCosts = spawnCosts;
      this.validSpawnBiomeForPlayer = isValidSpawnBiomeForPlayer;
   }

   public List<MobSpawnInfo.Spawners> getSpawners(EntityClassification classification) {
      return this.spawners.getOrDefault(classification, ImmutableList.of());
   }

   @Nullable
   public MobSpawnInfo.SpawnCosts getSpawnCost(EntityType<?> entityType) {
      return this.spawnCosts.get(entityType);
   }

   public float getCreatureSpawnProbability() {
      return this.creatureSpawnProbability;
   }

   public boolean isValidSpawnBiomeForPlayer() {
      return this.validSpawnBiomeForPlayer;
   }

   public static class Builder {
      private final Map<EntityClassification, List<MobSpawnInfo.Spawners>> spawners = Stream.of(EntityClassification.values()).collect(ImmutableMap.toImmutableMap((p_242578_0_) -> {
         return p_242578_0_;
      }, (p_242574_0_) -> {
         return Lists.newArrayList();
      }));
      private final Map<EntityType<?>, MobSpawnInfo.SpawnCosts> spawnCosts = Maps.newLinkedHashMap();
      private float creatureSpawnProbability = 0.1F;
      private boolean validSpawnBiomeForPlayer;

      public MobSpawnInfo.Builder withSpawner(EntityClassification classification, MobSpawnInfo.Spawners spawner) {
         this.spawners.get(classification).add(spawner);
         return this;
      }

      public MobSpawnInfo.Builder withSpawnCost(EntityType<?> entityType, double spawnCostPerEntity, double maxSpawnCost) {
         this.spawnCosts.put(entityType, new MobSpawnInfo.SpawnCosts(maxSpawnCost, spawnCostPerEntity));
         return this;
      }

      public MobSpawnInfo.Builder withCreatureSpawnProbability(float probability) {
         this.creatureSpawnProbability = probability;
         return this;
      }

      public MobSpawnInfo.Builder isValidSpawnBiomeForPlayer() {
         this.validSpawnBiomeForPlayer = true;
         return this;
      }

      public MobSpawnInfo copy() {
         return new MobSpawnInfo(this.creatureSpawnProbability, this.spawners.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_242576_0_) -> {
            return ImmutableList.copyOf((Collection)p_242576_0_.getValue());
         })), ImmutableMap.copyOf(this.spawnCosts), this.validSpawnBiomeForPlayer);
      }
   }

   public static class SpawnCosts {
      public static final Codec<MobSpawnInfo.SpawnCosts> CODEC = RecordCodecBuilder.create((p_242584_0_) -> {
         return p_242584_0_.group(Codec.DOUBLE.fieldOf("energy_budget").forGetter((p_242586_0_) -> {
            return p_242586_0_.maxSpawnCost;
         }), Codec.DOUBLE.fieldOf("charge").forGetter((p_242583_0_) -> {
            return p_242583_0_.entitySpawnCost;
         })).apply(p_242584_0_, MobSpawnInfo.SpawnCosts::new);
      });
      private final double maxSpawnCost;
      private final double entitySpawnCost;

      private SpawnCosts(double maxSpawnCost, double entitySpawnCost) {
         this.maxSpawnCost = maxSpawnCost;
         this.entitySpawnCost = entitySpawnCost;
      }

      public double getMaxSpawnCost() {
         return this.maxSpawnCost;
      }

      public double getEntitySpawnCost() {
         return this.entitySpawnCost;
      }
   }

   public static class Spawners extends WeightedRandom.Item {
      public static final Codec<MobSpawnInfo.Spawners> CODEC = RecordCodecBuilder.create((p_242592_0_) -> {
         return p_242592_0_.group(Registry.ENTITY_TYPE.fieldOf("type").forGetter((p_242595_0_) -> {
            return p_242595_0_.type;
         }), Codec.INT.fieldOf("weight").forGetter((p_242594_0_) -> {
            return p_242594_0_.itemWeight;
         }), Codec.INT.fieldOf("minCount").forGetter((p_242593_0_) -> {
            return p_242593_0_.minCount;
         }), Codec.INT.fieldOf("maxCount").forGetter((p_242591_0_) -> {
            return p_242591_0_.maxCount;
         })).apply(p_242592_0_, MobSpawnInfo.Spawners::new);
      });
      public final EntityType<?> type;
      public final int minCount;
      public final int maxCount;

      public Spawners(EntityType<?> type, int weight, int minCount, int maxCount) {
         super(weight);
         this.type = type.getClassification() == EntityClassification.MISC ? EntityType.PIG : type;
         this.minCount = minCount;
         this.maxCount = maxCount;
      }

      public String toString() {
         return EntityType.getKey(this.type) + "*(" + this.minCount + "-" + this.maxCount + "):" + this.itemWeight;
      }
   }
}
