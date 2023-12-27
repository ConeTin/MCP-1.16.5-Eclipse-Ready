package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class NoOpFeature extends Feature<NoFeatureConfig> {
   public NoOpFeature(Codec<NoFeatureConfig> p_i231973_1_) {
      super(p_i231973_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      return true;
   }
}
