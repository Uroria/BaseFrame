package com.uroria.base.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class ValidUtils {

    public void notNull(@Nullable Object object, @NonNull String message) throws NullPointerException {
        if (object != null) return;
        throw new NullPointerException(message);
    }

    public void checkBool(boolean val, @NonNull String message, boolean expectedBool) throws IllegalStateException {
        if (val == expectedBool) return;
        throw new IllegalStateException(message);
    }

    public void notFalse(boolean val, @NonNull String message) throws IllegalStateException {
        checkBool(val, message, true);
    }

    public void notTrue(boolean val, @NonNull String message) throws IllegalStateException {
        notFalse(!val, message);
    }
}
