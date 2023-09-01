package com.uroria.base.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@UtilityClass
public class ArrayUtils {

    public <T> @Nullable T getIndexOrNull(@NonNull T[] array, int index) {
        try {
            return array[index];
        } catch (Exception exception) {
            return null;
        }
    }

    public <T> @NotNull Optional<T> getIndex(@NonNull T[] array, int index) {
        return Optional.ofNullable(getIndexOrNull(array, index));
    }
}
