package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.Heightmap;

public class HeightmapPlacement<DC extends IPlacementConfig> extends SimpleHeightmapBasedPlacement<DC> {
   public HeightmapPlacement(Codec<DC> codec) {
      super(codec);
   }

   protected Heightmap.Type func_241858_a(DC config) {
      return Heightmap.Type.MOTION_BLOCKING;
   }
}
