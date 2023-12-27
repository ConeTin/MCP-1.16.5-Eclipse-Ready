package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class IcePathFeature extends AbstractSphereReplaceConfig {
   public IcePathFeature(Codec<SphereReplaceConfig> p_i231961_1_) {
      super(p_i231961_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, SphereReplaceConfig config) {
      while(reader.isAirBlock(pos) && pos.getY() > 2) {
         pos = pos.down();
      }

      return !reader.getBlockState(pos).isIn(Blocks.SNOW_BLOCK) ? false : super.generate(reader, generator, rand, pos, config);
   }
}
