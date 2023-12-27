package net.minecraft.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface IStringSerializable {
   String getString();

   static <E extends Enum<E> & IStringSerializable> Codec<E> createEnumCodec(Supplier<E[]> elementSupplier, Function<? super String, ? extends E> namingFunction) {
      E[] ae = elementSupplier.get();
      return createCodec(Enum::ordinal, (p_233026_1_) -> {
         return ae[p_233026_1_];
      }, namingFunction);
   }

   static <E extends IStringSerializable> Codec<E> createCodec(final ToIntFunction<E> elementSupplier, final IntFunction<E> selectorFunction, final Function<? super String, ? extends E> namingFunction) {
      return new Codec<E>() {
         public <T> DataResult<T> encode(E p_encode_1_, DynamicOps<T> p_encode_2_, T p_encode_3_) {
            return p_encode_2_.compressMaps() ? p_encode_2_.mergeToPrimitive(p_encode_3_, p_encode_2_.createInt(elementSupplier.applyAsInt(p_encode_1_))) : p_encode_2_.mergeToPrimitive(p_encode_3_, p_encode_2_.createString(p_encode_1_.getString()));
         }

         public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> p_decode_1_, T p_decode_2_) {
            return p_decode_1_.compressMaps() ? p_decode_1_.getNumberValue(p_decode_2_).flatMap((p_233034_1_) -> {
               return Optional.ofNullable(selectorFunction.apply(p_233034_1_.intValue())).map(DataResult::success).orElseGet(() -> {
                  return DataResult.error("Unknown element id: " + p_233034_1_);
               });
            }).map((p_233035_1_) -> {
               return Pair.of(p_233035_1_, p_decode_1_.empty());
            }) : p_decode_1_.getStringValue(p_decode_2_).flatMap((p_233033_1_) -> {
               return Optional.ofNullable(namingFunction.apply(p_233033_1_)).map(DataResult::success).orElseGet(() -> {
                  return DataResult.error("Unknown element name: " + p_233033_1_);
               });
            }).map((p_233030_1_) -> {
               return Pair.of(p_233030_1_, p_decode_1_.empty());
            });
         }

         public String toString() {
            return "StringRepresentable[" + elementSupplier + "]";
         }
      };
   }

   static Keyable createKeyable(final IStringSerializable[] serializables) {
      return new Keyable() {
         public <T> Stream<T> keys(DynamicOps<T> p_keys_1_) {
            return p_keys_1_.compressMaps() ? IntStream.range(0, serializables.length).mapToObj(p_keys_1_::createInt) : Arrays.stream(serializables).map(IStringSerializable::getString).map(p_keys_1_::createString);
         }
      };
   }
}
