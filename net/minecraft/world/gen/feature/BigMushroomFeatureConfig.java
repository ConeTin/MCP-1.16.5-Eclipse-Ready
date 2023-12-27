package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;

public class BigMushroomFeatureConfig implements IFeatureConfig {
   public static final Codec<BigMushroomFeatureConfig> CODEC = RecordCodecBuilder.create((p_236530_0_) -> {
      return p_236530_0_.group(BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter((p_236532_0_) -> {
         return p_236532_0_.capProvider;
      }), BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter((p_236531_0_) -> {
         return p_236531_0_.stemProvider;
      }), Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter((p_236529_0_) -> {
         return p_236529_0_.foliageRadius;
      })).apply(p_236530_0_, BigMushroomFeatureConfig::new);
   });
   public final BlockStateProvider capProvider;
   public final BlockStateProvider stemProvider;
   public final int foliageRadius;

   public BigMushroomFeatureConfig(BlockStateProvider capProvider, BlockStateProvider stemProvider, int foliageRadius) {
      this.capProvider = capProvider;
      this.stemProvider = stemProvider;
      this.foliageRadius = foliageRadius;
   }
}
