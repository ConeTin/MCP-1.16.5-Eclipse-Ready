package net.minecraft.world.biome.provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CheckerboardBiomeProvider extends BiomeProvider {
   public static final Codec<CheckerboardBiomeProvider> CODEC = RecordCodecBuilder.create((p_235258_0_) -> {
      return p_235258_0_.group(Biome.BIOMES_CODEC.fieldOf("biomes").forGetter((p_235259_0_) -> {
         return p_235259_0_.biomeList;
      }), Codec.intRange(0, 62).fieldOf("scale").orElse(2).forGetter((p_235257_0_) -> {
         return p_235257_0_.biomeScale;
      })).apply(p_235258_0_, CheckerboardBiomeProvider::new);
   });
   private final List<Supplier<Biome>> biomeList;
   private final int biomeScaleShift;
   private final int biomeScale;

   public CheckerboardBiomeProvider(List<Supplier<Biome>> biomes, int biomeScale) {
      super(biomes.stream());
      this.biomeList = biomes;
      this.biomeScaleShift = biomeScale + 2;
      this.biomeScale = biomeScale;
   }

   protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
      return CODEC;
   }

   @OnlyIn(Dist.CLIENT)
   public BiomeProvider getBiomeProvider(long seed) {
      return this;
   }

   public Biome getNoiseBiome(int x, int y, int z) {
      return this.biomeList.get(Math.floorMod((x >> this.biomeScaleShift) + (z >> this.biomeScaleShift), this.biomeList.size())).get();
   }
}
