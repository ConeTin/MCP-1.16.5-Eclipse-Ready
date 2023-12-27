package net.minecraft.world.gen.settings;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.Structure;

public class DimensionStructuresSettings {
   public static final Codec<DimensionStructuresSettings> field_236190_a_ = RecordCodecBuilder.create((p_236198_0_) -> {
      return p_236198_0_.group(StructureSpreadSettings.field_236656_a_.optionalFieldOf("stronghold").forGetter((p_236200_0_) -> {
         return Optional.ofNullable(p_236200_0_.field_236194_e_);
      }), Codec.simpleMap(Registry.STRUCTURE_FEATURE, StructureSeparationSettings.field_236664_a_, Registry.STRUCTURE_FEATURE).fieldOf("structures").forGetter((p_236196_0_) -> {
         return p_236196_0_.field_236193_d_;
      })).apply(p_236198_0_, DimensionStructuresSettings::new);
   });
   public static final ImmutableMap<Structure<?>, StructureSeparationSettings> field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().put(Structure.VILLAGE, new StructureSeparationSettings(32, 8, 10387312)).put(Structure.DESERT_PYRAMID, new StructureSeparationSettings(32, 8, 14357617)).put(Structure.IGLOO, new StructureSeparationSettings(32, 8, 14357618)).put(Structure.JUNGLE_PYRAMID, new StructureSeparationSettings(32, 8, 14357619)).put(Structure.SWAMP_HUT, new StructureSeparationSettings(32, 8, 14357620)).put(Structure.PILLAGER_OUTPOST, new StructureSeparationSettings(32, 8, 165745296)).put(Structure.STRONGHOLD, new StructureSeparationSettings(1, 0, 0)).put(Structure.MONUMENT, new StructureSeparationSettings(32, 5, 10387313)).put(Structure.END_CITY, new StructureSeparationSettings(20, 11, 10387313)).put(Structure.WOODLAND_MANSION, new StructureSeparationSettings(80, 20, 10387319)).put(Structure.BURIED_TREASURE, new StructureSeparationSettings(1, 0, 0)).put(Structure.MINESHAFT, new StructureSeparationSettings(1, 0, 0)).put(Structure.RUINED_PORTAL, new StructureSeparationSettings(40, 15, 34222645)).put(Structure.SHIPWRECK, new StructureSeparationSettings(24, 4, 165745295)).put(Structure.OCEAN_RUIN, new StructureSeparationSettings(20, 8, 14357621)).put(Structure.BASTION_REMNANT, new StructureSeparationSettings(27, 4, 30084232)).put(Structure.FORTRESS, new StructureSeparationSettings(27, 4, 30084232)).put(Structure.NETHER_FOSSIL, new StructureSeparationSettings(2, 1, 14357921)).build();
   public static final StructureSpreadSettings field_236192_c_;
   private final Map<Structure<?>, StructureSeparationSettings> field_236193_d_;
   @Nullable
   private final StructureSpreadSettings field_236194_e_;

   public DimensionStructuresSettings(Optional<StructureSpreadSettings> p_i231912_1_, Map<Structure<?>, StructureSeparationSettings> p_i231912_2_) {
      this.field_236194_e_ = p_i231912_1_.orElse((StructureSpreadSettings)null);
      this.field_236193_d_ = p_i231912_2_;
   }

   public DimensionStructuresSettings(boolean p_i231913_1_) {
      this.field_236193_d_ = Maps.newHashMap(field_236191_b_);
      this.field_236194_e_ = p_i231913_1_ ? field_236192_c_ : null;
   }

   public Map<Structure<?>, StructureSeparationSettings> func_236195_a_() {
      return this.field_236193_d_;
   }

   @Nullable
   public StructureSeparationSettings func_236197_a_(Structure<?> p_236197_1_) {
      return this.field_236193_d_.get(p_236197_1_);
   }

   @Nullable
   public StructureSpreadSettings func_236199_b_() {
      return this.field_236194_e_;
   }

   static {
      for(Structure<?> structure : Registry.STRUCTURE_FEATURE) {
         if (!field_236191_b_.containsKey(structure)) {
            throw new IllegalStateException("Structure feature without default settings: " + Registry.STRUCTURE_FEATURE.getKey(structure));
         }
      }

      field_236192_c_ = new StructureSpreadSettings(32, 3, 128);
   }
}
