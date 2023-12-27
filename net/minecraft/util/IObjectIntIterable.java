package net.minecraft.util;

import javax.annotation.Nullable;

public interface IObjectIntIterable<T> extends Iterable<T> {
   int getId(T value);

   @Nullable
   T getByValue(int value);
}
