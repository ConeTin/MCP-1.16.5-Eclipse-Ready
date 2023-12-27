package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class DecoratedFeature extends Feature<DecoratedFeatureConfig> {
   public DecoratedFeature(Codec<DecoratedFeatureConfig> p_i231943_1_) {
      super(p_i231943_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, DecoratedFeatureConfig config) {
      MutableBoolean mutableboolean = new MutableBoolean();
      config.decorator.func_242876_a(new WorldDecoratingHelper(reader, generator), rand, pos).forEach((p_242772_5_) -> {
         if (config.feature.get().generate(reader, generator, rand, p_242772_5_)) {
            mutableboolean.setTrue();
         }

      });
      return mutableboolean.isTrue();
   }

   public String toString() {
      return String.format("< %s [%s] >", this.getClass().getSimpleName(), Registry.FEATURE.getKey(this));
   }
}
