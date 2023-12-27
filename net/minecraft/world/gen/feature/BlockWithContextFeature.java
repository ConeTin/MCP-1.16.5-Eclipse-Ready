package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class BlockWithContextFeature extends Feature<BlockWithContextConfig> {
   public BlockWithContextFeature(Codec<BlockWithContextConfig> p_i231991_1_) {
      super(p_i231991_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockWithContextConfig config) {
      if (config.placeOn.contains(reader.getBlockState(pos.down())) && config.placeIn.contains(reader.getBlockState(pos)) && config.placeUnder.contains(reader.getBlockState(pos.up()))) {
         reader.setBlockState(pos, config.toPlace, 2);
         return true;
      } else {
         return false;
      }
   }
}
