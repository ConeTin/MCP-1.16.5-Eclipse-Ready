package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ItemParticleData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BreakingParticle extends SpriteTexturedParticle {
   private final float uCoord;
   private final float vCoord;

   private BreakingParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, ItemStack stack) {
      this(world, x, y, z, stack);
      this.motionX *= (double)0.1F;
      this.motionY *= (double)0.1F;
      this.motionZ *= (double)0.1F;
      this.motionX += motionX;
      this.motionY += motionY;
      this.motionZ += motionZ;
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.TERRAIN_SHEET;
   }

   protected BreakingParticle(ClientWorld world, double x, double y, double z, ItemStack stack) {
      super(world, x, y, z, 0.0D, 0.0D, 0.0D);
      this.setSprite(Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(stack, world, (LivingEntity)null).getParticleTexture());
      this.particleGravity = 1.0F;
      this.particleScale /= 2.0F;
      this.uCoord = this.rand.nextFloat() * 3.0F;
      this.vCoord = this.rand.nextFloat() * 3.0F;
   }

   protected float getMinU() {
      return this.sprite.getInterpolatedU((double)((this.uCoord + 1.0F) / 4.0F * 16.0F));
   }

   protected float getMaxU() {
      return this.sprite.getInterpolatedU((double)(this.uCoord / 4.0F * 16.0F));
   }

   protected float getMinV() {
      return this.sprite.getInterpolatedV((double)(this.vCoord / 4.0F * 16.0F));
   }

   protected float getMaxV() {
      return this.sprite.getInterpolatedV((double)((this.vCoord + 1.0F) / 4.0F * 16.0F));
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<ItemParticleData> {
      public Particle makeParticle(ItemParticleData typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new BreakingParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn.getItemStack());
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class SlimeFactory implements IParticleFactory<BasicParticleType> {
      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new BreakingParticle(worldIn, x, y, z, new ItemStack(Items.SLIME_BALL));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class SnowballFactory implements IParticleFactory<BasicParticleType> {
      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new BreakingParticle(worldIn, x, y, z, new ItemStack(Items.SNOWBALL));
      }
   }
}
