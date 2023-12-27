package net.minecraft.client.renderer.model;

import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IModelTransform {
   default TransformationMatrix getRotation() {
      return TransformationMatrix.identity();
   }

   default boolean isUvLock() {
      return false;
   }
}
