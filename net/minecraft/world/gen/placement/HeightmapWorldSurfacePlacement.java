package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.Heightmap;

public class HeightmapWorldSurfacePlacement extends SimpleHeightmapBasedPlacement<NoPlacementConfig> {
   public HeightmapWorldSurfacePlacement(Codec<NoPlacementConfig> codec) {
      super(codec);
   }

   protected Heightmap.Type func_241858_a(NoPlacementConfig config) {
      return Heightmap.Type.WORLD_SURFACE_WG;
   }
}
