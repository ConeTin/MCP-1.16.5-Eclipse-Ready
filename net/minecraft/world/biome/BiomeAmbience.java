package net.minecraft.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BiomeAmbience {
   public static final Codec<BiomeAmbience> CODEC = RecordCodecBuilder.create((p_235215_0_) -> {
      return p_235215_0_.group(Codec.INT.fieldOf("fog_color").forGetter((p_235229_0_) -> {
         return p_235229_0_.fogColor;
      }), Codec.INT.fieldOf("water_color").forGetter((p_235227_0_) -> {
         return p_235227_0_.waterColor;
      }), Codec.INT.fieldOf("water_fog_color").forGetter((p_235225_0_) -> {
         return p_235225_0_.waterFogColor;
      }), Codec.INT.fieldOf("sky_color").forGetter((p_242532_0_) -> {
         return p_242532_0_.skyColor;
      }), Codec.INT.optionalFieldOf("foliage_color").forGetter((p_244421_0_) -> {
         return p_244421_0_.foliageColor;
      }), Codec.INT.optionalFieldOf("grass_color").forGetter((p_244426_0_) -> {
         return p_244426_0_.grassColor;
      }), BiomeAmbience.GrassColorModifier.CODEC.optionalFieldOf("grass_color_modifier", BiomeAmbience.GrassColorModifier.NONE).forGetter((p_242530_0_) -> {
         return p_242530_0_.grassColorModifier;
      }), ParticleEffectAmbience.CODEC.optionalFieldOf("particle").forGetter((p_235223_0_) -> {
         return p_235223_0_.particle;
      }), SoundEvent.CODEC.optionalFieldOf("ambient_sound").forGetter((p_235221_0_) -> {
         return p_235221_0_.ambientSound;
      }), MoodSoundAmbience.CODEC.optionalFieldOf("mood_sound").forGetter((p_235219_0_) -> {
         return p_235219_0_.moodSound;
      }), SoundAdditionsAmbience.CODEC.optionalFieldOf("additions_sound").forGetter((p_235217_0_) -> {
         return p_235217_0_.additionsSound;
      }), BackgroundMusicSelector.CODEC.optionalFieldOf("music").forGetter((p_244420_0_) -> {
         return p_244420_0_.music;
      })).apply(p_235215_0_, BiomeAmbience::new);
   });
   private final int fogColor;
   private final int waterColor;
   private final int waterFogColor;
   private final int skyColor;
   private final Optional<Integer> foliageColor;
   private final Optional<Integer> grassColor;
   private final BiomeAmbience.GrassColorModifier grassColorModifier;
   private final Optional<ParticleEffectAmbience> particle;
   private final Optional<SoundEvent> ambientSound;
   private final Optional<MoodSoundAmbience> moodSound;
   private final Optional<SoundAdditionsAmbience> additionsSound;
   private final Optional<BackgroundMusicSelector> music;

   private BiomeAmbience(int fogColor, int waterColor, int waterFogColor, int skyColor, Optional<Integer> foliageColor, Optional<Integer> grassColor, BiomeAmbience.GrassColorModifier grassColorModifier, Optional<ParticleEffectAmbience> particle, Optional<SoundEvent> ambientSound, Optional<MoodSoundAmbience> moodSound, Optional<SoundAdditionsAmbience> additionsSound, Optional<BackgroundMusicSelector> music) {
      this.fogColor = fogColor;
      this.waterColor = waterColor;
      this.waterFogColor = waterFogColor;
      this.skyColor = skyColor;
      this.foliageColor = foliageColor;
      this.grassColor = grassColor;
      this.grassColorModifier = grassColorModifier;
      this.particle = particle;
      this.ambientSound = ambientSound;
      this.moodSound = moodSound;
      this.additionsSound = additionsSound;
      this.music = music;
   }

   @OnlyIn(Dist.CLIENT)
   public int getFogColor() {
      return this.fogColor;
   }

   @OnlyIn(Dist.CLIENT)
   public int getWaterColor() {
      return this.waterColor;
   }

   @OnlyIn(Dist.CLIENT)
   public int getWaterFogColor() {
      return this.waterFogColor;
   }

   @OnlyIn(Dist.CLIENT)
   public int getSkyColor() {
      return this.skyColor;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<Integer> getFoliageColor() {
      return this.foliageColor;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<Integer> getGrassColor() {
      return this.grassColor;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeAmbience.GrassColorModifier getGrassColorModifier() {
      return this.grassColorModifier;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<ParticleEffectAmbience> getParticle() {
      return this.particle;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundEvent> getAmbientSound() {
      return this.ambientSound;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<MoodSoundAmbience> getMoodSound() {
      return this.moodSound;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundAdditionsAmbience> getAdditionsSound() {
      return this.additionsSound;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<BackgroundMusicSelector> getMusic() {
      return this.music;
   }

   public static class Builder {
      private OptionalInt fogColor = OptionalInt.empty();
      private OptionalInt waterColor = OptionalInt.empty();
      private OptionalInt waterFogColor = OptionalInt.empty();
      private OptionalInt skyColor = OptionalInt.empty();
      private Optional<Integer> foliageColor = Optional.empty();
      private Optional<Integer> grassColor = Optional.empty();
      private BiomeAmbience.GrassColorModifier grassColorModifier = BiomeAmbience.GrassColorModifier.NONE;
      private Optional<ParticleEffectAmbience> particle = Optional.empty();
      private Optional<SoundEvent> ambientSound = Optional.empty();
      private Optional<MoodSoundAmbience> moodSound = Optional.empty();
      private Optional<SoundAdditionsAmbience> additionsSound = Optional.empty();
      private Optional<BackgroundMusicSelector> music = Optional.empty();

      public BiomeAmbience.Builder setFogColor(int fogColor) {
         this.fogColor = OptionalInt.of(fogColor);
         return this;
      }

      public BiomeAmbience.Builder setWaterColor(int waterColor) {
         this.waterColor = OptionalInt.of(waterColor);
         return this;
      }

      public BiomeAmbience.Builder setWaterFogColor(int waterFogColor) {
         this.waterFogColor = OptionalInt.of(waterFogColor);
         return this;
      }

      public BiomeAmbience.Builder withSkyColor(int skyColor) {
         this.skyColor = OptionalInt.of(skyColor);
         return this;
      }

      public BiomeAmbience.Builder withFoliageColor(int foliageColor) {
         this.foliageColor = Optional.of(foliageColor);
         return this;
      }

      public BiomeAmbience.Builder withGrassColor(int grassColor) {
         this.grassColor = Optional.of(grassColor);
         return this;
      }

      public BiomeAmbience.Builder withGrassColorModifier(BiomeAmbience.GrassColorModifier grassColorModifier) {
         this.grassColorModifier = grassColorModifier;
         return this;
      }

      public BiomeAmbience.Builder setParticle(ParticleEffectAmbience particle) {
         this.particle = Optional.of(particle);
         return this;
      }

      public BiomeAmbience.Builder setAmbientSound(SoundEvent ambientSound) {
         this.ambientSound = Optional.of(ambientSound);
         return this;
      }

      public BiomeAmbience.Builder setMoodSound(MoodSoundAmbience moodSound) {
         this.moodSound = Optional.of(moodSound);
         return this;
      }

      public BiomeAmbience.Builder setAdditionsSound(SoundAdditionsAmbience additionsSound) {
         this.additionsSound = Optional.of(additionsSound);
         return this;
      }

      public BiomeAmbience.Builder setMusic(BackgroundMusicSelector music) {
         this.music = Optional.of(music);
         return this;
      }

      public BiomeAmbience build() {
         return new BiomeAmbience(this.fogColor.orElseThrow(() -> {
            return new IllegalStateException("Missing 'fog' color.");
         }), this.waterColor.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water' color.");
         }), this.waterFogColor.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water fog' color.");
         }), this.skyColor.orElseThrow(() -> {
            return new IllegalStateException("Missing 'sky' color.");
         }), this.foliageColor, this.grassColor, this.grassColorModifier, this.particle, this.ambientSound, this.moodSound, this.additionsSound, this.music);
      }
   }

   public static enum GrassColorModifier implements IStringSerializable {
      NONE("none") {
         @OnlyIn(Dist.CLIENT)
         public int getModifiedGrassColor(double x, double z, int grassColor) {
            return grassColor;
         }
      },
      DARK_FOREST("dark_forest") {
         @OnlyIn(Dist.CLIENT)
         public int getModifiedGrassColor(double x, double z, int grassColor) {
            return (grassColor & 16711422) + 2634762 >> 1;
         }
      },
      SWAMP("swamp") {
         @OnlyIn(Dist.CLIENT)
         public int getModifiedGrassColor(double x, double z, int grassColor) {
            double d0 = Biome.INFO_NOISE.noiseAt(x * 0.0225D, z * 0.0225D, false);
            return d0 < -0.1D ? 5011004 : 6975545;
         }
      };

      private final String name;
      public static final Codec<BiomeAmbience.GrassColorModifier> CODEC = IStringSerializable.createEnumCodec(BiomeAmbience.GrassColorModifier::values, BiomeAmbience.GrassColorModifier::byName);
      private static final Map<String, BiomeAmbience.GrassColorModifier> NAME_TO_MODIFIER_MAP = Arrays.stream(values()).collect(Collectors.toMap(BiomeAmbience.GrassColorModifier::getName, (p_242545_0_) -> {
         return p_242545_0_;
      }));

      @OnlyIn(Dist.CLIENT)
      public abstract int getModifiedGrassColor(double x, double z, int grassColor);

      private GrassColorModifier(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public String getString() {
         return this.name;
      }

      public static BiomeAmbience.GrassColorModifier byName(String name) {
         return NAME_TO_MODIFIER_MAP.get(name);
      }
   }
}
