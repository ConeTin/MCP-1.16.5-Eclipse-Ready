package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.state.Property;
import net.minecraft.util.registry.Registry;

public class BlockState extends AbstractBlock.AbstractBlockState {
   public static final Codec<BlockState> CODEC = func_235897_a_(Registry.BLOCK, Block::getDefaultState).stable();

   public BlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> propertiesToValueMap, MapCodec<BlockState> codec) {
      super(block, propertiesToValueMap, codec);
   }

   protected BlockState getSelf() {
      return this;
   }
}
