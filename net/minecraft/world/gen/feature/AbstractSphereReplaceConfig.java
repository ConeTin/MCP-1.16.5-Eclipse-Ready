package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class AbstractSphereReplaceConfig extends Feature<SphereReplaceConfig> {
   public AbstractSphereReplaceConfig(Codec<SphereReplaceConfig> codec) {
      super(codec);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, SphereReplaceConfig config) {
      boolean flag = false;
      int i = config.radius.func_242259_a(rand);

      for(int j = pos.getX() - i; j <= pos.getX() + i; ++j) {
         for(int k = pos.getZ() - i; k <= pos.getZ() + i; ++k) {
            int l = j - pos.getX();
            int i1 = k - pos.getZ();
            if (l * l + i1 * i1 <= i * i) {
               for(int j1 = pos.getY() - config.field_242809_d; j1 <= pos.getY() + config.field_242809_d; ++j1) {
                  BlockPos blockpos = new BlockPos(j, j1, k);
                  Block block = reader.getBlockState(blockpos).getBlock();

                  for(BlockState blockstate : config.targets) {
                     if (blockstate.isIn(block)) {
                        reader.setBlockState(blockpos, config.state, 2);
                        flag = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      return flag;
   }
}
