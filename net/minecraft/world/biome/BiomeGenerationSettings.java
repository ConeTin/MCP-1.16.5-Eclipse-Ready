package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Util;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeGenerationSettings {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final BiomeGenerationSettings DEFAULT_SETTINGS = new BiomeGenerationSettings(() -> {
      return ConfiguredSurfaceBuilders.field_244184_p;
   }, ImmutableMap.of(), ImmutableList.of(), ImmutableList.of());
   public static final MapCodec<BiomeGenerationSettings> CODEC = RecordCodecBuilder.mapCodec((p_242495_0_) -> {
      return p_242495_0_.group(ConfiguredSurfaceBuilder.field_244393_b_.fieldOf("surface_builder").forGetter((p_242501_0_) -> {
         return p_242501_0_.surfaceBuilder;
      }), Codec.simpleMap(GenerationStage.Carving.CODEC, ConfiguredCarver.field_242759_c.promotePartial(Util.func_240982_a_("Carver: ", LOGGER::error)), IStringSerializable.createKeyable(GenerationStage.Carving.values())).fieldOf("carvers").forGetter((p_242499_0_) -> {
         return p_242499_0_.carvers;
      }), ConfiguredFeature.field_242764_c.promotePartial(Util.func_240982_a_("Feature: ", LOGGER::error)).listOf().fieldOf("features").forGetter((p_242497_0_) -> {
         return p_242497_0_.features;
      }), StructureFeature.field_242770_c.promotePartial(Util.func_240982_a_("Structure start: ", LOGGER::error)).fieldOf("starts").forGetter((p_242488_0_) -> {
         return p_242488_0_.structures;
      })).apply(p_242495_0_, BiomeGenerationSettings::new);
   });
   private final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder;
   private final Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> carvers;
   private final List<List<Supplier<ConfiguredFeature<?, ?>>>> features;
   private final List<Supplier<StructureFeature<?, ?>>> structures;
   private final List<ConfiguredFeature<?, ?>> flowerFeatures;

   private BiomeGenerationSettings(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> carversIn, List<List<Supplier<ConfiguredFeature<?, ?>>>> features, List<Supplier<StructureFeature<?, ?>>> structures) {
      this.surfaceBuilder = surfaceBuilder;
      this.carvers = carversIn;
      this.features = features;
      this.structures = structures;
      this.flowerFeatures = features.stream().flatMap(Collection::stream).map(Supplier::get).flatMap(ConfiguredFeature::func_242768_d).filter((p_242490_0_) -> {
         return p_242490_0_.feature == Feature.FLOWER;
      }).collect(ImmutableList.toImmutableList());
   }

   public List<Supplier<ConfiguredCarver<?>>> getCarvers(GenerationStage.Carving carvingType) {
      return this.carvers.getOrDefault(carvingType, ImmutableList.of());
   }

   public boolean hasStructure(Structure<?> structure) {
      return this.structures.stream().anyMatch((p_242494_1_) -> {
         return (p_242494_1_.get()).field_236268_b_ == structure;
      });
   }

   public Collection<Supplier<StructureFeature<?, ?>>> getStructures() {
      return this.structures;
   }

   public StructureFeature<?, ?> getStructure(StructureFeature<?, ?> structure) {
      return DataFixUtils.orElse(this.structures.stream().map(Supplier::get).filter((p_242492_1_) -> {
         return p_242492_1_.field_236268_b_ == structure.field_236268_b_;
      }).findAny(), structure);
   }

   public List<ConfiguredFeature<?, ?>> getFlowerFeatures() {
      return this.flowerFeatures;
   }

   public List<List<Supplier<ConfiguredFeature<?, ?>>>> getFeatures() {
      return this.features;
   }

   public Supplier<ConfiguredSurfaceBuilder<?>> getSurfaceBuilder() {
      return this.surfaceBuilder;
   }

   public ISurfaceBuilderConfig getSurfaceBuilderConfig() {
      return this.surfaceBuilder.get().getConfig();
   }

   public static class Builder {
      private Optional<Supplier<ConfiguredSurfaceBuilder<?>>> surfaceBuilder = Optional.empty();
      private final Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> carvers = Maps.newLinkedHashMap();
      private final List<List<Supplier<ConfiguredFeature<?, ?>>>> features = Lists.newArrayList();
      private final List<Supplier<StructureFeature<?, ?>>> structures = Lists.newArrayList();

      public BiomeGenerationSettings.Builder withSurfaceBuilder(ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder) {
         return this.withSurfaceBuilder(() -> {
            return configuredSurfaceBuilder;
         });
      }

      public BiomeGenerationSettings.Builder withSurfaceBuilder(Supplier<ConfiguredSurfaceBuilder<?>> configuredSurfaceBuilderSupplier) {
         this.surfaceBuilder = Optional.of(configuredSurfaceBuilderSupplier);
         return this;
      }

      public BiomeGenerationSettings.Builder withFeature(GenerationStage.Decoration decorationStage, ConfiguredFeature<?, ?> feature) {
         return this.withFeature(decorationStage.ordinal(), () -> {
            return feature;
         });
      }

      public BiomeGenerationSettings.Builder withFeature(int stage, Supplier<ConfiguredFeature<?, ?>> features) {
         this.populateStageEntries(stage);
         this.features.get(stage).add(features);
         return this;
      }

      public <C extends ICarverConfig> BiomeGenerationSettings.Builder withCarver(GenerationStage.Carving carvingStage, ConfiguredCarver<C> carver) {
         this.carvers.computeIfAbsent(carvingStage, (p_242511_0_) -> {
            return Lists.newArrayList();
         }).add(() -> {
            return carver;
         });
         return this;
      }

      public BiomeGenerationSettings.Builder withStructure(StructureFeature<?, ?> structure) {
         this.structures.add(() -> {
            return structure;
         });
         return this;
      }

      private void populateStageEntries(int stage) {
         while(this.features.size() <= stage) {
            this.features.add(Lists.newArrayList());
         }

      }

      public BiomeGenerationSettings build() {
         return new BiomeGenerationSettings(this.surfaceBuilder.orElseThrow(() -> {
            return new IllegalStateException("Missing surface builder");
         }), this.carvers.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_242518_0_) -> {
            return ImmutableList.copyOf((Collection)p_242518_0_.getValue());
         })), this.features.stream().map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList()), ImmutableList.copyOf(this.structures));
      }
   }
}
