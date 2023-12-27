package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;

public class BaseTreeFeatureConfig implements IFeatureConfig {
   public static final Codec<BaseTreeFeatureConfig> CODEC = RecordCodecBuilder.create((p_236683_0_) -> {
      return p_236683_0_.group(BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter((p_236693_0_) -> {
         return p_236693_0_.trunkProvider;
      }), BlockStateProvider.CODEC.fieldOf("leaves_provider").forGetter((p_236692_0_) -> {
         return p_236692_0_.leavesProvider;
      }), FoliagePlacer.field_236749_d_.fieldOf("foliage_placer").forGetter((p_236691_0_) -> {
         return p_236691_0_.foliagePlacer;
      }), AbstractTrunkPlacer.field_236905_c_.fieldOf("trunk_placer").forGetter((p_236690_0_) -> {
         return p_236690_0_.trunkPlacer;
      }), AbstractFeatureSizeType.CODEC.fieldOf("minimum_size").forGetter((p_236689_0_) -> {
         return p_236689_0_.minimumSize;
      }), TreeDecorator.field_236874_c_.listOf().fieldOf("decorators").forGetter((p_236688_0_) -> {
         return p_236688_0_.decorators;
      }), Codec.INT.fieldOf("max_water_depth").orElse(0).forGetter((p_236687_0_) -> {
         return p_236687_0_.maxWaterDepth;
      }), Codec.BOOL.fieldOf("ignore_vines").orElse(false).forGetter((p_236686_0_) -> {
         return p_236686_0_.ignoreVines;
      }), Heightmap.Type.CODEC.fieldOf("heightmap").forGetter((p_236684_0_) -> {
         return p_236684_0_.field_236682_l_;
      })).apply(p_236683_0_, BaseTreeFeatureConfig::new);
   });
   public final BlockStateProvider trunkProvider;
   public final BlockStateProvider leavesProvider;
   public final List<TreeDecorator> decorators;
   public transient boolean forcePlacement;
   public final FoliagePlacer foliagePlacer;
   public final AbstractTrunkPlacer trunkPlacer;
   public final AbstractFeatureSizeType minimumSize;
   public final int maxWaterDepth;
   public final boolean ignoreVines;
   public final Heightmap.Type field_236682_l_;

   protected BaseTreeFeatureConfig(BlockStateProvider trunkProvider, BlockStateProvider leavesProvider, FoliagePlacer foliagePlacer, AbstractTrunkPlacer trunkPlacer, AbstractFeatureSizeType minimumSize, List<TreeDecorator> decorators, int maxWaterDepth, boolean ignoreVines, Heightmap.Type p_i232020_9_) {
      this.trunkProvider = trunkProvider;
      this.leavesProvider = leavesProvider;
      this.decorators = decorators;
      this.foliagePlacer = foliagePlacer;
      this.minimumSize = minimumSize;
      this.trunkPlacer = trunkPlacer;
      this.maxWaterDepth = maxWaterDepth;
      this.ignoreVines = ignoreVines;
      this.field_236682_l_ = p_i232020_9_;
   }

   public void forcePlacement() {
      this.forcePlacement = true;
   }

   public BaseTreeFeatureConfig func_236685_a_(List<TreeDecorator> decorators) {
      return new BaseTreeFeatureConfig(this.trunkProvider, this.leavesProvider, this.foliagePlacer, this.trunkPlacer, this.minimumSize, decorators, this.maxWaterDepth, this.ignoreVines, this.field_236682_l_);
   }

   public static class Builder {
      public final BlockStateProvider trunkProvider;
      public final BlockStateProvider leavesProvider;
      private final FoliagePlacer foliagePlacer;
      private final AbstractTrunkPlacer trunkPlacer;
      private final AbstractFeatureSizeType minimumSize;
      private List<TreeDecorator> decorators = ImmutableList.of();
      private int maxWaterDepth;
      private boolean ignoreVines;
      private Heightmap.Type field_236699_i_ = Heightmap.Type.OCEAN_FLOOR;

      public Builder(BlockStateProvider trunkProvider, BlockStateProvider leavesProvider, FoliagePlacer foliagePlacer, AbstractTrunkPlacer trunkPlacer, AbstractFeatureSizeType minimumSize) {
         this.trunkProvider = trunkProvider;
         this.leavesProvider = leavesProvider;
         this.foliagePlacer = foliagePlacer;
         this.trunkPlacer = trunkPlacer;
         this.minimumSize = minimumSize;
      }

      public BaseTreeFeatureConfig.Builder setDecorators(List<TreeDecorator> decorators) {
         this.decorators = decorators;
         return this;
      }

      public BaseTreeFeatureConfig.Builder setMaxWaterDepth(int p_236701_1_) {
         this.maxWaterDepth = p_236701_1_;
         return this;
      }

      public BaseTreeFeatureConfig.Builder setIgnoreVines() {
         this.ignoreVines = true;
         return this;
      }

      public BaseTreeFeatureConfig.Builder func_236702_a_(Heightmap.Type p_236702_1_) {
         this.field_236699_i_ = p_236702_1_;
         return this;
      }

      public BaseTreeFeatureConfig build() {
         return new BaseTreeFeatureConfig(this.trunkProvider, this.leavesProvider, this.foliagePlacer, this.trunkPlacer, this.minimumSize, this.decorators, this.maxWaterDepth, this.ignoreVines, this.field_236699_i_);
      }
   }
}
