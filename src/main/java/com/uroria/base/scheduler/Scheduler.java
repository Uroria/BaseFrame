package com.uroria.base.scheduler;

import lombok.NonNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Scheduler {
    <T> AsyncTask<T> runTask(@NonNull Supplier<? extends T> action);

    <T> AsyncTask<T> runTaskLater(@NonNull Supplier<? extends T> action, long time, @NonNull TimeUnit timeUnit);

    <T> AsyncTask<T> runTaskTimer(@NonNull Function<Integer, ? extends T> action, long time, @NonNull TimeUnit timeUnit);
}
