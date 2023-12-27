package net.minecraft.util.datafix.codec;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class DatapackCodec {
   public static final DatapackCodec VANILLA_CODEC = new DatapackCodec(ImmutableList.of("vanilla"), ImmutableList.of());
   public static final Codec<DatapackCodec> CODEC = RecordCodecBuilder.create((p_234886_0_) -> {
      return p_234886_0_.group(Codec.STRING.listOf().fieldOf("Enabled").forGetter((p_234888_0_) -> {
         return p_234888_0_.enabled;
      }), Codec.STRING.listOf().fieldOf("Disabled").forGetter((p_234885_0_) -> {
         return p_234885_0_.disabled;
      })).apply(p_234886_0_, DatapackCodec::new);
   });
   private final List<String> enabled;
   private final List<String> disabled;

   public DatapackCodec(List<String> enabled, List<String> disabled) {
      this.enabled = ImmutableList.copyOf(enabled);
      this.disabled = ImmutableList.copyOf(disabled);
   }

   public List<String> getEnabled() {
      return this.enabled;
   }

   public List<String> getDisabled() {
      return this.disabled;
   }
}
