package net.minecraft.entity.ai.brain;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

public class Memory<T> {
   private final T value;
   private long timeToLive;

   public Memory(T value, long timeToLive) {
      this.value = value;
      this.timeToLive = timeToLive;
   }

   public void tick() {
      if (this.isForgettable()) {
         --this.timeToLive;
      }

   }

   public static <T> Memory<T> create(T value) {
      return new Memory<>(value, Long.MAX_VALUE);
   }

   public static <T> Memory<T> create(T value, long timeToLive) {
      return new Memory<>(value, timeToLive);
   }

   public T getValue() {
      return this.value;
   }

   public boolean isForgotten() {
      return this.timeToLive <= 0L;
   }

   public String toString() {
      return this.value.toString() + (this.isForgettable() ? " (ttl: " + this.timeToLive + ")" : "");
   }

   public boolean isForgettable() {
      return this.timeToLive != Long.MAX_VALUE;
   }

   public static <T> Codec<Memory<T>> createCodec(Codec<T> valueCodec) {
      return RecordCodecBuilder.create((p_234067_1_) -> {
         return p_234067_1_.group(valueCodec.fieldOf("value").forGetter((p_234071_0_) -> {
            return p_234071_0_.value;
         }), Codec.LONG.optionalFieldOf("ttl").forGetter((p_234065_0_) -> {
            return p_234065_0_.isForgettable() ? Optional.of(p_234065_0_.timeToLive) : Optional.empty();
         })).apply(p_234067_1_, (p_234070_0_, p_234070_1_) -> {
            return new Memory<>(p_234070_0_, p_234070_1_.orElse(Long.MAX_VALUE));
         });
      });
   }
}
