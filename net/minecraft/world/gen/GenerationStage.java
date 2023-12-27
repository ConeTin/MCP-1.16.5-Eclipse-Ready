package net.minecraft.world.gen;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.IStringSerializable;

public class GenerationStage {
   public static enum Carving implements IStringSerializable {
      AIR("air"),
      LIQUID("liquid");

      public static final Codec<GenerationStage.Carving> CODEC = IStringSerializable.createEnumCodec(GenerationStage.Carving::values, GenerationStage.Carving::getByName);
      private static final Map<String, GenerationStage.Carving> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(GenerationStage.Carving::getName, (p_222672_0_) -> {
         return p_222672_0_;
      }));
      private final String name;

      private Carving(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      @Nullable
      public static GenerationStage.Carving getByName(String name) {
         return BY_NAME.get(name);
      }

      public String getString() {
         return this.name;
      }
   }

   public static enum Decoration {
      RAW_GENERATION,
      LAKES,
      LOCAL_MODIFICATIONS,
      UNDERGROUND_STRUCTURES,
      SURFACE_STRUCTURES,
      STRONGHOLDS,
      UNDERGROUND_ORES,
      UNDERGROUND_DECORATION,
      VEGETAL_DECORATION,
      TOP_LAYER_MODIFICATION;
   }
}
