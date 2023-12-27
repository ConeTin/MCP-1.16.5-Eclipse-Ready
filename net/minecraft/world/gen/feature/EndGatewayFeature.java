package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class EndGatewayFeature extends Feature<EndGatewayConfig> {
   public EndGatewayFeature(Codec<EndGatewayConfig> p_i231951_1_) {
      super(p_i231951_1_);
   }

   public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, EndGatewayConfig config) {
      for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -2, -1), pos.add(1, 2, 1))) {
         boolean flag = blockpos.getX() == pos.getX();
         boolean flag1 = blockpos.getY() == pos.getY();
         boolean flag2 = blockpos.getZ() == pos.getZ();
         boolean flag3 = Math.abs(blockpos.getY() - pos.getY()) == 2;
         if (flag && flag1 && flag2) {
            BlockPos blockpos1 = blockpos.toImmutable();
            this.setBlockState(reader, blockpos1, Blocks.END_GATEWAY.getDefaultState());
            config.func_214700_b().ifPresent((p_236280_3_) -> {
               TileEntity tileentity = reader.getTileEntity(blockpos1);
               if (tileentity instanceof EndGatewayTileEntity) {
                  EndGatewayTileEntity endgatewaytileentity = (EndGatewayTileEntity)tileentity;
                  endgatewaytileentity.setExitPortal(p_236280_3_, config.func_214701_c());
                  tileentity.markDirty();
               }

            });
         } else if (flag1) {
            this.setBlockState(reader, blockpos, Blocks.AIR.getDefaultState());
         } else if (flag3 && flag && flag2) {
            this.setBlockState(reader, blockpos, Blocks.BEDROCK.getDefaultState());
         } else if ((flag || flag2) && !flag3) {
            this.setBlockState(reader, blockpos, Blocks.BEDROCK.getDefaultState());
         } else {
            this.setBlockState(reader, blockpos, Blocks.AIR.getDefaultState());
         }
      }

      return true;
   }
}
