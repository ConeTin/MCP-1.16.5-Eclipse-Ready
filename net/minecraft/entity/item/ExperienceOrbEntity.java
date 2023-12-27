package net.minecraft.entity.item;

import java.util.Map.Entry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnExperienceOrbPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExperienceOrbEntity extends Entity {
   public int xpColor;
   public int xpOrbAge;
   public int delayBeforeCanPickup;
   private int xpOrbHealth = 5;
   private int xpValue;
   private PlayerEntity closestPlayer;
   private int xpTargetColor;

   public ExperienceOrbEntity(World worldIn, double x, double y, double z, int expValue) {
      this(EntityType.EXPERIENCE_ORB, worldIn);
      this.setPosition(x, y, z);
      this.rotationYaw = (float)(this.rand.nextDouble() * 360.0D);
      this.setMotion((this.rand.nextDouble() * (double)0.2F - (double)0.1F) * 2.0D, this.rand.nextDouble() * 0.2D * 2.0D, (this.rand.nextDouble() * (double)0.2F - (double)0.1F) * 2.0D);
      this.xpValue = expValue;
   }

   public ExperienceOrbEntity(EntityType<? extends ExperienceOrbEntity> p_i50382_1_, World entity) {
      super(p_i50382_1_, entity);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void registerData() {
   }

   public void tick() {
      super.tick();
      if (this.delayBeforeCanPickup > 0) {
         --this.delayBeforeCanPickup;
      }

      this.prevPosX = this.getPosX();
      this.prevPosY = this.getPosY();
      this.prevPosZ = this.getPosZ();
      if (this.areEyesInFluid(FluidTags.WATER)) {
         this.applyFloatMotion();
      } else if (!this.hasNoGravity()) {
         this.setMotion(this.getMotion().add(0.0D, -0.03D, 0.0D));
      }

      if (this.world.getFluidState(this.getPosition()).isTagged(FluidTags.LAVA)) {
         this.setMotion((double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F), (double)0.2F, (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
         this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
      }

      if (!this.world.hasNoCollisions(this.getBoundingBox())) {
         this.pushOutOfBlocks(this.getPosX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getPosZ());
      }

      double d0 = 8.0D;
      if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
         if (this.closestPlayer == null || this.closestPlayer.getDistanceSq(this) > 64.0D) {
            this.closestPlayer = this.world.getClosestPlayer(this, 8.0D);
         }

         this.xpTargetColor = this.xpColor;
      }

      if (this.closestPlayer != null && this.closestPlayer.isSpectator()) {
         this.closestPlayer = null;
      }

      if (this.closestPlayer != null) {
         Vector3d vector3d = new Vector3d(this.closestPlayer.getPosX() - this.getPosX(), this.closestPlayer.getPosY() + (double)this.closestPlayer.getEyeHeight() / 2.0D - this.getPosY(), this.closestPlayer.getPosZ() - this.getPosZ());
         double d1 = vector3d.lengthSquared();
         if (d1 < 64.0D) {
            double d2 = 1.0D - Math.sqrt(d1) / 8.0D;
            this.setMotion(this.getMotion().add(vector3d.normalize().scale(d2 * d2 * 0.1D)));
         }
      }

      this.move(MoverType.SELF, this.getMotion());
      float f = 0.98F;
      if (this.onGround) {
         f = this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ())).getBlock().getSlipperiness() * 0.98F;
      }

      this.setMotion(this.getMotion().mul((double)f, 0.98D, (double)f));
      if (this.onGround) {
         this.setMotion(this.getMotion().mul(1.0D, -0.9D, 1.0D));
      }

      ++this.xpColor;
      ++this.xpOrbAge;
      if (this.xpOrbAge >= 6000) {
         this.remove();
      }

   }

   private void applyFloatMotion() {
      Vector3d vector3d = this.getMotion();
      this.setMotion(vector3d.x * (double)0.99F, Math.min(vector3d.y + (double)5.0E-4F, (double)0.06F), vector3d.z * (double)0.99F);
   }

   protected void doWaterSplashEffect() {
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         this.markVelocityChanged();
         this.xpOrbHealth = (int)((float)this.xpOrbHealth - amount);
         if (this.xpOrbHealth <= 0) {
            this.remove();
         }

         return false;
      }
   }

   public void writeAdditional(CompoundNBT compound) {
      compound.putShort("Health", (short)this.xpOrbHealth);
      compound.putShort("Age", (short)this.xpOrbAge);
      compound.putShort("Value", (short)this.xpValue);
   }

   public void readAdditional(CompoundNBT compound) {
      this.xpOrbHealth = compound.getShort("Health");
      this.xpOrbAge = compound.getShort("Age");
      this.xpValue = compound.getShort("Value");
   }

   public void onCollideWithPlayer(PlayerEntity entityIn) {
      if (!this.world.isRemote) {
         if (this.delayBeforeCanPickup == 0 && entityIn.xpCooldown == 0) {
            entityIn.xpCooldown = 2;
            entityIn.onItemPickup(this, 1);
            Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomEquippedWithEnchantment(Enchantments.MENDING, entityIn, ItemStack::isDamaged);
            if (entry != null) {
               ItemStack itemstack = entry.getValue();
               if (!itemstack.isEmpty() && itemstack.isDamaged()) {
                  int i = Math.min(this.xpToDurability(this.xpValue), itemstack.getDamage());
                  this.xpValue -= this.durabilityToXp(i);
                  itemstack.setDamage(itemstack.getDamage() - i);
               }
            }

            if (this.xpValue > 0) {
               entityIn.giveExperiencePoints(this.xpValue);
            }

            this.remove();
         }

      }
   }

   private int durabilityToXp(int durability) {
      return durability / 2;
   }

   private int xpToDurability(int xp) {
      return xp * 2;
   }

   public int getXpValue() {
      return this.xpValue;
   }

   @OnlyIn(Dist.CLIENT)
   public int getTextureByXP() {
      if (this.xpValue >= 2477) {
         return 10;
      } else if (this.xpValue >= 1237) {
         return 9;
      } else if (this.xpValue >= 617) {
         return 8;
      } else if (this.xpValue >= 307) {
         return 7;
      } else if (this.xpValue >= 149) {
         return 6;
      } else if (this.xpValue >= 73) {
         return 5;
      } else if (this.xpValue >= 37) {
         return 4;
      } else if (this.xpValue >= 17) {
         return 3;
      } else if (this.xpValue >= 7) {
         return 2;
      } else {
         return this.xpValue >= 3 ? 1 : 0;
      }
   }

   public static int getXPSplit(int expValue) {
      if (expValue >= 2477) {
         return 2477;
      } else if (expValue >= 1237) {
         return 1237;
      } else if (expValue >= 617) {
         return 617;
      } else if (expValue >= 307) {
         return 307;
      } else if (expValue >= 149) {
         return 149;
      } else if (expValue >= 73) {
         return 73;
      } else if (expValue >= 37) {
         return 37;
      } else if (expValue >= 17) {
         return 17;
      } else if (expValue >= 7) {
         return 7;
      } else {
         return expValue >= 3 ? 3 : 1;
      }
   }

   public boolean canBeAttackedWithItem() {
      return false;
   }

   public IPacket<?> createSpawnPacket() {
      return new SSpawnExperienceOrbPacket(this);
   }
}
