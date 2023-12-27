package net.minecraft.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SoundAdditionsAmbience {
   public static final Codec<SoundAdditionsAmbience> CODEC = RecordCodecBuilder.create((p_235023_0_) -> {
      return p_235023_0_.group(SoundEvent.CODEC.fieldOf("sound").forGetter((p_235025_0_) -> {
         return p_235025_0_.sound;
      }), Codec.DOUBLE.fieldOf("tick_chance").forGetter((p_235022_0_) -> {
         return p_235022_0_.tickChance;
      })).apply(p_235023_0_, SoundAdditionsAmbience::new);
   });
   private SoundEvent sound;
   private double tickChance;

   public SoundAdditionsAmbience(SoundEvent sound, double tickChance) {
      this.sound = sound;
      this.tickChance = tickChance;
   }

   @OnlyIn(Dist.CLIENT)
   public SoundEvent getSound() {
      return this.sound;
   }

   @OnlyIn(Dist.CLIENT)
   public double getChancePerTick() {
      return this.tickChance;
   }
}
