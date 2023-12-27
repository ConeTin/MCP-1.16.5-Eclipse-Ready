package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;

public class BonusChestFeature extends Feature<NoFeatureConfig> {
   public BonusChestFeature(Codec<NoFeatureConfig> p_i231934_1_) {
      super(p_i231934_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      ChunkPos chunkpos = new ChunkPos(pos);
      List<Integer> list = IntStream.rangeClosed(chunkpos.getXStart(), chunkpos.getXEnd()).boxed().collect(Collectors.toList());
      Collections.shuffle(list, rand);
      List<Integer> list1 = IntStream.rangeClosed(chunkpos.getZStart(), chunkpos.getZEnd()).boxed().collect(Collectors.toList());
      Collections.shuffle(list1, rand);
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

      for(Integer integer : list) {
         for(Integer integer1 : list1) {
            blockpos$mutable.setPos(integer, 0, integer1);
            BlockPos blockpos = reader.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos$mutable);
            if (reader.isAirBlock(blockpos) || reader.getBlockState(blockpos).getCollisionShape(reader, blockpos).isEmpty()) {
               reader.setBlockState(blockpos, Blocks.CHEST.getDefaultState(), 2);
               LockableLootTileEntity.setLootTable(reader, rand, blockpos, LootTables.CHESTS_SPAWN_BONUS_CHEST);
               BlockState blockstate = Blocks.TORCH.getDefaultState();

               for(Direction direction : Direction.Plane.HORIZONTAL) {
                  BlockPos blockpos1 = blockpos.offset(direction);
                  if (blockstate.isValidPosition(reader, blockpos1)) {
                     reader.setBlockState(blockpos1, blockstate, 2);
                  }
               }

               return true;
            }
         }
      }

      return false;
   }
}
