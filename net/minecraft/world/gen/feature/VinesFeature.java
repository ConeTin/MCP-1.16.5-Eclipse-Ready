package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class VinesFeature extends Feature<NoFeatureConfig> {
   private static final Direction[] DIRECTIONS = Direction.values();

   public VinesFeature(Codec<NoFeatureConfig> p_i232002_1_) {
      super(p_i232002_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      BlockPos.Mutable blockpos$mutable = pos.toMutable();

      for(int i = 64; i < 256; ++i) {
         blockpos$mutable.setPos(pos);
         blockpos$mutable.move(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
         blockpos$mutable.setY(i);
         if (reader.isAirBlock(blockpos$mutable)) {
            for(Direction direction : DIRECTIONS) {
               if (direction != Direction.DOWN && VineBlock.canAttachTo(reader, blockpos$mutable, direction)) {
                  reader.setBlockState(blockpos$mutable, Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(direction), Boolean.valueOf(true)), 2);
                  break;
               }
            }
         }
      }

      return true;
   }
}
