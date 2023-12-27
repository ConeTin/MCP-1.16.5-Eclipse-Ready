package net.minecraft.client.audio;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.SoundAdditionsAmbience;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiomeSoundHandler implements IAmbientSoundHandler {
   private final ClientPlayerEntity player;
   private final SoundHandler soundHandler;
   private final BiomeManager biomeManager;
   private final Random random;
   private Object2ObjectArrayMap<Biome, BiomeSoundHandler.Sound> activeBiomeSoundsMap = new Object2ObjectArrayMap<>();
   private Optional<MoodSoundAmbience> currentAmbientMoodSound = Optional.empty();
   private Optional<SoundAdditionsAmbience> currentAmbientAdditionalSound = Optional.empty();
   private float darknessAmbienceChance;
   private Biome currentBiome;

   public BiomeSoundHandler(ClientPlayerEntity player, SoundHandler soundHandler, BiomeManager biomeManager) {
      this.random = player.world.getRandom();
      this.player = player;
      this.soundHandler = soundHandler;
      this.biomeManager = biomeManager;
   }

   public float getDarknessAmbienceChance() {
      return this.darknessAmbienceChance;
   }

   public void tick() {
      this.activeBiomeSoundsMap.values().removeIf(TickableSound::isDonePlaying);
      Biome biome = this.biomeManager.getBiomeAtPosition(this.player.getPosX(), this.player.getPosY(), this.player.getPosZ());
      if (biome != this.currentBiome) {
         this.currentBiome = biome;
         this.currentAmbientMoodSound = biome.getMoodSound();
         this.currentAmbientAdditionalSound = biome.getAdditionalAmbientSound();
         this.activeBiomeSoundsMap.values().forEach(BiomeSoundHandler.Sound::fadeOutSound);
         biome.getAmbientSound().ifPresent((p_239522_2_) -> {
            BiomeSoundHandler.Sound biomesoundhandler$sound = this.activeBiomeSoundsMap.compute(biome, (p_239519_2_, p_239519_3_) -> {
               if (p_239519_3_ == null) {
                  p_239519_3_ = new BiomeSoundHandler.Sound(p_239522_2_);
                  this.soundHandler.play(p_239519_3_);
               }

               p_239519_3_.fadeInSound();
               return p_239519_3_;
            });
         });
      }

      this.currentAmbientAdditionalSound.ifPresent((p_239520_1_) -> {
         if (this.random.nextDouble() < p_239520_1_.getChancePerTick()) {
            this.soundHandler.play(SimpleSound.ambient(p_239520_1_.getSound()));
         }

      });
      this.currentAmbientMoodSound.ifPresent((p_239521_1_) -> {
         World world = this.player.world;
         int i = p_239521_1_.getSearchRadius() * 2 + 1;
         BlockPos blockpos = new BlockPos(this.player.getPosX() + (double)this.random.nextInt(i) - (double)p_239521_1_.getSearchRadius(), this.player.getPosYEye() + (double)this.random.nextInt(i) - (double)p_239521_1_.getSearchRadius(), this.player.getPosZ() + (double)this.random.nextInt(i) - (double)p_239521_1_.getSearchRadius());
         int j = world.getLightFor(LightType.SKY, blockpos);
         if (j > 0) {
            this.darknessAmbienceChance -= (float)j / (float)world.getMaxLightLevel() * 0.001F;
         } else {
            this.darknessAmbienceChance -= (float)(world.getLightFor(LightType.BLOCK, blockpos) - 1) / (float)p_239521_1_.getTickDelay();
         }

         if (this.darknessAmbienceChance >= 1.0F) {
            double d0 = (double)blockpos.getX() + 0.5D;
            double d1 = (double)blockpos.getY() + 0.5D;
            double d2 = (double)blockpos.getZ() + 0.5D;
            double d3 = d0 - this.player.getPosX();
            double d4 = d1 - this.player.getPosYEye();
            double d5 = d2 - this.player.getPosZ();
            double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
            double d7 = d6 + p_239521_1_.getOffset();
            SimpleSound simplesound = SimpleSound.ambientWithAttenuation(p_239521_1_.getSound(), this.player.getPosX() + d3 / d6 * d7, this.player.getPosYEye() + d4 / d6 * d7, this.player.getPosZ() + d5 / d6 * d7);
            this.soundHandler.play(simplesound);
            this.darknessAmbienceChance = 0.0F;
         } else {
            this.darknessAmbienceChance = Math.max(this.darknessAmbienceChance, 0.0F);
         }

      });
   }

   @OnlyIn(Dist.CLIENT)
   public static class Sound extends TickableSound {
      private int fadeSpeed;
      private int fadeInTicks;

      public Sound(SoundEvent sound) {
         super(sound, SoundCategory.AMBIENT);
         this.repeat = true;
         this.repeatDelay = 0;
         this.volume = 1.0F;
         this.global = true;
      }

      public void tick() {
         if (this.fadeInTicks < 0) {
            this.finishPlaying();
         }

         this.fadeInTicks += this.fadeSpeed;
         this.volume = MathHelper.clamp((float)this.fadeInTicks / 40.0F, 0.0F, 1.0F);
      }

      public void fadeOutSound() {
         this.fadeInTicks = Math.min(this.fadeInTicks, 40);
         this.fadeSpeed = -1;
      }

      public void fadeInSound() {
         this.fadeInTicks = Math.max(0, this.fadeInTicks);
         this.fadeSpeed = 1;
      }
   }
}
