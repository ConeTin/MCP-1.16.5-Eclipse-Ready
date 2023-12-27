package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class DepthAverageConfig implements IPlacementConfig {
   public static final Codec<DepthAverageConfig> CODEC = RecordCodecBuilder.create((p_236956_0_) -> {
      return p_236956_0_.group(Codec.INT.fieldOf("baseline").forGetter((p_236959_0_) -> {
         return p_236959_0_.baseline;
      }), Codec.INT.fieldOf("spread").forGetter((p_236958_0_) -> {
         return p_236958_0_.spread;
      })).apply(p_236956_0_, DepthAverageConfig::new);
   });
   public final int baseline;
   public final int spread;

   public DepthAverageConfig(int baseline, int spread) {
      this.baseline = baseline;
      this.spread = spread;
   }
}
