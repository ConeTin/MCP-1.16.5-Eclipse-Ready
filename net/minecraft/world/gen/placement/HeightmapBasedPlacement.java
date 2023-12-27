package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.Heightmap;

public abstract class HeightmapBasedPlacement<DC extends IPlacementConfig> extends Placement<DC> {
   public HeightmapBasedPlacement(Codec<DC> codec) {
      super(codec);
   }

   protected abstract Heightmap.Type func_241858_a(DC config);
}
