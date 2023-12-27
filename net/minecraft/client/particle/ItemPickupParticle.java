package net.minecraft.client.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemPickupParticle extends Particle {
   private final RenderTypeBuffers renderTypeBuffers;
   private final Entity item;
   private final Entity target;
   private int particleAge;
   private final EntityRendererManager renderManager;

   public ItemPickupParticle(EntityRendererManager entityRenderManager, RenderTypeBuffers buffers, ClientWorld world, Entity item, Entity target) {
      this(entityRenderManager, buffers, world, item, target, item.getMotion());
   }

   private ItemPickupParticle(EntityRendererManager entityRenderManager, RenderTypeBuffers buffers, ClientWorld world, Entity item, Entity target, Vector3d motionVector) {
      super(world, item.getPosX(), item.getPosY(), item.getPosZ(), motionVector.x, motionVector.y, motionVector.z);
      this.renderTypeBuffers = buffers;
      this.item = this.func_239181_a_(item);
      this.target = target;
      this.renderManager = entityRenderManager;
   }

   private Entity func_239181_a_(Entity entity) {
      return (Entity)(!(entity instanceof ItemEntity) ? entity : ((ItemEntity)entity).func_234273_t_());
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.CUSTOM;
   }

   public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
      float f = ((float)this.particleAge + partialTicks) / 3.0F;
      f = f * f;
      double d0 = MathHelper.lerp((double)partialTicks, this.target.lastTickPosX, this.target.getPosX());
      double d1 = MathHelper.lerp((double)partialTicks, this.target.lastTickPosY, this.target.getPosY()) + 0.5D;
      double d2 = MathHelper.lerp((double)partialTicks, this.target.lastTickPosZ, this.target.getPosZ());
      double d3 = MathHelper.lerp((double)f, this.item.getPosX(), d0);
      double d4 = MathHelper.lerp((double)f, this.item.getPosY(), d1);
      double d5 = MathHelper.lerp((double)f, this.item.getPosZ(), d2);
      IRenderTypeBuffer.Impl irendertypebuffer$impl = this.renderTypeBuffers.getBufferSource();
      Vector3d vector3d = renderInfo.getProjectedView();
      this.renderManager.renderEntityStatic(this.item, d3 - vector3d.getX(), d4 - vector3d.getY(), d5 - vector3d.getZ(), this.item.rotationYaw, partialTicks, new MatrixStack(), irendertypebuffer$impl, this.renderManager.getPackedLight(this.item, partialTicks));
      irendertypebuffer$impl.finish();
   }

   public void tick() {
      ++this.particleAge;
      if (this.particleAge == 3) {
         this.setExpired();
      }

   }
}
