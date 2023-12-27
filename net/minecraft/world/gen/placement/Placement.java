package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;

public abstract class Placement<DC extends IPlacementConfig> {
   public static final Placement<NoPlacementConfig> NOPE = register("nope", new Passthrough(NoPlacementConfig.CODEC));
   public static final Placement<ChanceConfig> CHANCE = register("chance", new ChancePlacement(ChanceConfig.CODEC));
   public static final Placement<FeatureSpreadConfig> COUNT = register("count", new CountPlacement(FeatureSpreadConfig.CODEC));
   public static final Placement<NoiseDependant> COUNT_NOISE = register("count_noise", new CountNoisePlacement(NoiseDependant.CODEC));
   public static final Placement<TopSolidWithNoiseConfig> COUNT_NOISE_BIASED = register("count_noise_biased", new CountNoiseBiasedPlacement(TopSolidWithNoiseConfig.CODEC));
   public static final Placement<AtSurfaceWithExtraConfig> COUNT_EXTRA = register("count_extra", new CountExtraPlacement(AtSurfaceWithExtraConfig.CODEC));
   public static final Placement<NoPlacementConfig> SQUARE = register("square", new SquarePlacement(NoPlacementConfig.CODEC));
   public static final Placement<NoPlacementConfig> HEIGHTMAP = register("heightmap", new HeightmapPlacement<>(NoPlacementConfig.CODEC));
   public static final Placement<NoPlacementConfig> HEIGHTMAP_SPREAD_DOUBLE = register("heightmap_spread_double", new HeightmapSpreadDoublePlacement<>(NoPlacementConfig.CODEC));
   public static final Placement<NoPlacementConfig> TOP_SOLID_HEIGHTMAP = register("top_solid_heightmap", new TopSolidOnce(NoPlacementConfig.CODEC));
   public static final Placement<NoPlacementConfig> HEIGHTMAP_WORLD_SURFACE = register("heightmap_world_surface", new HeightmapWorldSurfacePlacement(NoPlacementConfig.CODEC));
   public static final Placement<TopSolidRangeConfig> RANGE = register("range", new RangePlacement(TopSolidRangeConfig.CODEC));
   public static final Placement<TopSolidRangeConfig> RANGE_BIASED = register("range_biased", new RangeBiasedPlacement(TopSolidRangeConfig.CODEC));
   public static final Placement<TopSolidRangeConfig> RANGE_VERY_BIASED = register("range_very_biased", new RangeVeryBiasedPlacement(TopSolidRangeConfig.CODEC));
   public static final Placement<DepthAverageConfig> DEPTH_AVERAGE = register("depth_average", new DepthAveragePlacement(DepthAverageConfig.CODEC));
   public static final Placement<NoPlacementConfig> SPREAD_32_ABOVE = register("spread_32_above", new Spread32AbovePlacement(NoPlacementConfig.CODEC));
   public static final Placement<CaveEdgeConfig> CARVING_MASK = register("carving_mask", new CaveEdge(CaveEdgeConfig.CODEC));
   public static final Placement<FeatureSpreadConfig> FIRE = register("fire", new FirePlacement(FeatureSpreadConfig.CODEC));
   public static final Placement<NoPlacementConfig> MAGMA = register("magma", new NetherMagma(NoPlacementConfig.CODEC));
   public static final Placement<NoPlacementConfig> EMERALD_ORE = register("emerald_ore", new Height4To32(NoPlacementConfig.CODEC));
   public static final Placement<ChanceConfig> LAVA_LAKE = register("lava_lake", new LakeLava(ChanceConfig.CODEC));
   public static final Placement<ChanceConfig> WATER_LAKE = register("water_lake", new LakeWater(ChanceConfig.CODEC));
   public static final Placement<FeatureSpreadConfig> GLOWSTONE = register("glowstone", new GlowstonePlacement(FeatureSpreadConfig.CODEC));
   public static final Placement<NoPlacementConfig> END_GATEWAY = register("end_gateway", new EndGateway(NoPlacementConfig.CODEC));
   public static final Placement<NoPlacementConfig> DARK_OAK_TREE = register("dark_oak_tree", new DarkOakTreePlacement(NoPlacementConfig.CODEC));
   public static final Placement<NoPlacementConfig> ICEBERG = register("iceberg", new IcebergPlacement(NoPlacementConfig.CODEC));
   public static final Placement<NoPlacementConfig> END_ISLAND = register("end_island", new EndIsland(NoPlacementConfig.CODEC));
   public static final Placement<DecoratedPlacementConfig> DECORATED = register("decorated", new DecoratedPlacement(DecoratedPlacementConfig.CODEC));
   public static final Placement<FeatureSpreadConfig> COUNT_MULTILAYER = register("count_multilayer", new CountMultilayerPlacement(FeatureSpreadConfig.CODEC));
   private final Codec<ConfiguredPlacement<DC>> codec;

   private static <T extends IPlacementConfig, G extends Placement<T>> G register(String key, G placement) {
      return Registry.register(Registry.DECORATOR, key, placement);
   }

   public Placement(Codec<DC> codec) {
      this.codec = codec.fieldOf("config").xmap((p_236966_1_) -> {
         return new ConfiguredPlacement<DC>(this, p_236966_1_);
      }, ConfiguredPlacement::func_242877_b).codec();
   }

   public ConfiguredPlacement<DC> configure(DC config) {
      return new ConfiguredPlacement<>(this, config);
   }

   public Codec<ConfiguredPlacement<DC>> getCodec() {
      return this.codec;
   }

   public abstract Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, DC config, BlockPos pos);

   public String toString() {
      return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
   }
}
