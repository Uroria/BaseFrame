package com.uroria.base.scheduler;

import com.uroria.base.utils.Pair;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public final class AsyncTaskFactory {

    public static <T> AsyncTask<T> create(@NonNull String name, @NonNull ScheduledExecutorService service, @NonNull Supplier<? extends T> supplier, Executor backSync) {
        return new AsyncTaskImpl<>(supplier, service, LoggerFactory.getLogger(name), backSync);
    }

    public static <T> AsyncTask<T> create(@NonNull String name, @NonNull ScheduledExecutorService service, @NonNull Supplier<? extends T> supplier, long time, @NonNull TimeUnit timeUnit, Executor backSync) {
        return new AsyncTaskImpl<>(supplier, time, timeUnit, service, LoggerFactory.getLogger(name), backSync);
    }

    public static <T> AsyncTask<T> create(@NonNull String name, @NonNull ScheduledExecutorService service, @NonNull Function<Integer, ? extends T> function, long time, @NonNull TimeUnit timeUnit, Executor backSync) {
        return new AsyncTaskImpl<>(service, backSync, LoggerFactory.getLogger(name), function, time, timeUnit);
    }

    private static final class AsyncTaskImpl<T> implements AsyncTask<T> {
        private final ScheduledExecutorService executorService;
        private final Executor backSync;
        private final Logger logger;
        private final Function<Integer, ? extends T> function;
        private final long time;
        private final TimeUnit timeUnit;

        private AsyncTaskImpl(Supplier<? extends T> supplier, ScheduledExecutorService executorService, Logger logger, Executor backSync) {
            this(executorService, backSync, logger, ignored -> supplier.get(), 0, TimeUnit.MILLISECONDS);
        }

        private AsyncTaskImpl(Supplier<? extends T> supplier, long time, TimeUnit timeUnit, ScheduledExecutorService executorService, Logger logger, Executor backSync) {
            this(executorService, backSync, logger, ignored -> supplier.get(), time, timeUnit);
        }

        private AsyncTaskImpl(ScheduledExecutorService executorService, Executor backSync, Logger logger, Function<Integer, ? extends T> function, long time, TimeUnit timeUnit) {
            this.executorService = executorService;
            this.backSync = backSync;
            this.logger = logger;
            this.function = function;
            this.time = time;
            this.timeUnit = timeUnit;
        }

        @Override
        public void run(@NotNull Consumer<? super T> success) {
            run(success, this::onError);
        }

        @Override
        public void run(@NotNull Consumer<? super T> success, @NotNull Consumer<? super Throwable> error) {
            CompletableFuture<T> future = time == 0 ? submit() : submitLater(time, timeUnit);
            future.whenComplete((onSuccess, onError) -> backSync.execute(() -> {
                if (future.isCompletedExceptionally()) {
                    error.accept(onError);
                    return;
                }
                success.accept(onSuccess);
            }));
        }

        @Override
        public void run(@NotNull BiConsumer<? super T, ? super ScheduledFuture<?>> success) {
            run(success, (throwable, runnable) -> onError(throwable));
        }

        @Override
        public void run(@NotNull BiConsumer<? super T, ? super ScheduledFuture<?>> success, @NotNull BiConsumer<? super Throwable, ? super ScheduledFuture<?>> error) {
            submitTimer(success, error);
        }

        private CompletableFuture<T> submit() {
            CompletableFuture<T> future = new CompletableFuture<T>();
            processFuture(future, 1);
            return future;
        }

        private CompletableFuture<T> submitLater(long time, TimeUnit timeUnit) {
            CompletableFuture<T> future = new CompletableFuture<T>();
            executorService.schedule(() -> processFuture(future, 1), time, timeUnit);
            return future;
        }

        private void submitTimer(BiConsumer<? super T, ? super ScheduledFuture<?>> success, BiConsumer<? super Throwable, ? super ScheduledFuture<?>> error) {
            AtomicReference<ScheduledFuture<?>> scheduledFuture = new AtomicReference<ScheduledFuture<?>>(null);
            AtomicInteger counter = new AtomicInteger(1);
            scheduledFuture.set(executorService.scheduleAtFixedRate(() -> {

                var future = new CompletableFuture<T>();
                processFuture(future, counter.getAndIncrement());

                future.whenComplete(((result, throwable) -> backSync.execute(() -> {
                    if (future.isCompletedExceptionally()) {
                        error.accept(throwable, scheduledFuture.get());
                        return;
                    }

                    success.accept(result, scheduledFuture.get());
                })));

            }, time, time, timeUnit));
        }

        private void processFuture(CompletableFuture<? super T> future, Integer counter) {
            Pair<T, Throwable> result = execute(counter).join();
            if (result.getSecond() != null) future.completeExceptionally(result.getSecond());
            else future.complete(result.getFirst());
        }


        private ForkJoinTask<Pair<T, Throwable>> execute(Integer counter) {
            return ForkJoinPool.commonPool().submit(ForkJoinTask.adapt(() -> {
                try {
                    return new Pair<>(this.function.apply(counter), null);
                } catch (Throwable throwable) {
                    return new Pair<>(null, throwable);
                }
            }));
        }

        private void onError(Throwable throwable) {
            logger.error("Unhandled Exception in AsyncTask", throwable);
        }
    }
}
