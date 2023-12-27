package net.minecraft.world.gen.feature.structure;

import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.RuinedPortalFeature;
import net.minecraft.world.gen.feature.StructureFeature;

public class StructureFeatures {
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> PILLAGER_OUTPOST = register("pillager_outpost", Structure.PILLAGER_OUTPOST.withConfiguration(new VillageConfig(() -> {
      return PillagerOutpostPools.field_244088_a;
   }, 7)));
   public static final StructureFeature<MineshaftConfig, ? extends Structure<MineshaftConfig>> MINESHAFT = register("mineshaft", Structure.MINESHAFT.withConfiguration(new MineshaftConfig(0.004F, MineshaftStructure.Type.NORMAL)));
   public static final StructureFeature<MineshaftConfig, ? extends Structure<MineshaftConfig>> MINESHAFT_BADLANDS = register("mineshaft_mesa", Structure.MINESHAFT.withConfiguration(new MineshaftConfig(0.004F, MineshaftStructure.Type.MESA)));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> MANSION = register("mansion", Structure.WOODLAND_MANSION.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> JUNGLE_PYRAMID = register("jungle_pyramid", Structure.JUNGLE_PYRAMID.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> DESERT_PYRAMID = register("desert_pyramid", Structure.DESERT_PYRAMID.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> IGLOO = register("igloo", Structure.IGLOO.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<ShipwreckConfig, ? extends Structure<ShipwreckConfig>> SHIPWRECK = register("shipwreck", Structure.SHIPWRECK.withConfiguration(new ShipwreckConfig(false)));
   public static final StructureFeature<ShipwreckConfig, ? extends Structure<ShipwreckConfig>> SHIPWRECK_BEACHED = register("shipwreck_beached", Structure.SHIPWRECK.withConfiguration(new ShipwreckConfig(true)));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> SWAMP_HUT = register("swamp_hut", Structure.SWAMP_HUT.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> STRONGHOLD = register("stronghold", Structure.STRONGHOLD.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> MONUMENT = register("monument", Structure.MONUMENT.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<OceanRuinConfig, ? extends Structure<OceanRuinConfig>> OCEAN_RUIN_COLD = register("ocean_ruin_cold", Structure.OCEAN_RUIN.withConfiguration(new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F)));
   public static final StructureFeature<OceanRuinConfig, ? extends Structure<OceanRuinConfig>> OCEAN_RUIN_WARM = register("ocean_ruin_warm", Structure.OCEAN_RUIN.withConfiguration(new OceanRuinConfig(OceanRuinStructure.Type.WARM, 0.3F, 0.9F)));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> FORTRESS = register("fortress", Structure.FORTRESS.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> NETHER_FOSSIL = register("nether_fossil", Structure.NETHER_FOSSIL.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> END_CITY = register("end_city", Structure.END_CITY.withConfiguration(NoFeatureConfig.field_236559_b_));
   public static final StructureFeature<ProbabilityConfig, ? extends Structure<ProbabilityConfig>> BURIED_TREASURE = register("buried_treasure", Structure.BURIED_TREASURE.withConfiguration(new ProbabilityConfig(0.01F)));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> BASTION_REMNANT = register("bastion_remnant", Structure.BASTION_REMNANT.withConfiguration(new VillageConfig(() -> {
      return BastionRemnantsPieces.field_243686_a;
   }, 6)));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> VILLAGE_PLAINS = register("village_plains", Structure.VILLAGE.withConfiguration(new VillageConfig(() -> {
      return PlainsVillagePools.field_244090_a;
   }, 6)));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> VILLAGE_DESERT = register("village_desert", Structure.VILLAGE.withConfiguration(new VillageConfig(() -> {
      return DesertVillagePools.field_243774_a;
   }, 6)));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> VILLAGE_SAVANNA = register("village_savanna", Structure.VILLAGE.withConfiguration(new VillageConfig(() -> {
      return SavannaVillagePools.field_244128_a;
   }, 6)));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> VILLAGE_SNOWY = register("village_snowy", Structure.VILLAGE.withConfiguration(new VillageConfig(() -> {
      return SnowyVillagePools.field_244129_a;
   }, 6)));
   public static final StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> VILLAGE_TAIGA = register("village_taiga", Structure.VILLAGE.withConfiguration(new VillageConfig(() -> {
      return TaigaVillagePools.field_244193_a;
   }, 6)));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> RUINED_PORTAL = register("ruined_portal", Structure.RUINED_PORTAL.withConfiguration(new RuinedPortalFeature(RuinedPortalStructure.Location.STANDARD)));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> RUINED_PORTAL_DESERT = register("ruined_portal_desert", Structure.RUINED_PORTAL.withConfiguration(new RuinedPortalFeature(RuinedPortalStructure.Location.DESERT)));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> RUINED_PORTAL_JUNGLE = register("ruined_portal_jungle", Structure.RUINED_PORTAL.withConfiguration(new RuinedPortalFeature(RuinedPortalStructure.Location.JUNGLE)));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> RUINED_PORTAL_SWAMP = register("ruined_portal_swamp", Structure.RUINED_PORTAL.withConfiguration(new RuinedPortalFeature(RuinedPortalStructure.Location.SWAMP)));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> RUINED_PORTAL_MOUNTAIN = register("ruined_portal_mountain", Structure.RUINED_PORTAL.withConfiguration(new RuinedPortalFeature(RuinedPortalStructure.Location.MOUNTAIN)));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> RUINED_PORTAL_OCEAN = register("ruined_portal_ocean", Structure.RUINED_PORTAL.withConfiguration(new RuinedPortalFeature(RuinedPortalStructure.Location.OCEAN)));
   public static final StructureFeature<RuinedPortalFeature, ? extends Structure<RuinedPortalFeature>> RUINED_PORTAL_NETHER = register("ruined_portal_nether", Structure.RUINED_PORTAL.withConfiguration(new RuinedPortalFeature(RuinedPortalStructure.Location.NETHER)));

   private static <FC extends IFeatureConfig, F extends Structure<FC>> StructureFeature<FC, F> register(String name, StructureFeature<FC, F> structure) {
      return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, name, structure);
   }
}
