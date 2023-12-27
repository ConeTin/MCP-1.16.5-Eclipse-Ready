package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;

public class NoPlacementConfig implements IPlacementConfig {
   public static final Codec<NoPlacementConfig> CODEC;
   public static final NoPlacementConfig INSTANCE = new NoPlacementConfig();

   static {
      CODEC = Codec.unit(() -> {
         return INSTANCE;
      });
   }
}
