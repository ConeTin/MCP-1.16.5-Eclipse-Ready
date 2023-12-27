package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MetaParticle extends Particle {
   protected MetaParticle(ClientWorld world, double x, double y, double z) {
      super(world, x, y, z);
   }

   protected MetaParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
      super(world, x, y, z, motionX, motionY, motionZ);
   }

   public final void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.NO_RENDER;
   }
}
