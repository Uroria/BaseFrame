package com.uroria.base.scheduler;

import lombok.NonNull;

import java.util.concurrent.ScheduledFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface AsyncTask<T> {

    void run(@NonNull Consumer<? super T> success);

    void run(@NonNull Consumer<? super T> success, @NonNull Consumer<? super Throwable> error);

    void run(@NonNull BiConsumer<? super T, ? super ScheduledFuture<?>> success);

    void run(@NonNull BiConsumer<? super T, ? super ScheduledFuture<?>> success, @NonNull BiConsumer<? super Throwable, ? super ScheduledFuture<?>> error);
}
