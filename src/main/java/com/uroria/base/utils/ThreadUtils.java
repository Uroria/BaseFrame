package com.uroria.base.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class ThreadUtils {

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ignored) {}
    }

    public void sleep(long val, @NonNull TimeUnit unit) {
        sleep(unit.toMillis(val));
    }
}
