package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class SphereReplaceFeature extends AbstractSphereReplaceConfig {
   public SphereReplaceFeature(Codec<SphereReplaceConfig> p_i231949_1_) {
      super(p_i231949_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, SphereReplaceConfig config) {
      return !reader.getFluidState(pos).isTagged(FluidTags.WATER) ? false : super.generate(reader, generator, rand, pos, config);
   }
}
