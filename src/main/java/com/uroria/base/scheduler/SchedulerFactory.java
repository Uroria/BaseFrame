package com.uroria.base.scheduler;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public final class SchedulerFactory {

    public static Scheduler create(@NonNull String name) {
        return new SchedulerImpl(name, Executors.newScheduledThreadPool(0));
    }

    public static Scheduler create(@NonNull String name, @NonNull ScheduledExecutorService service) {
        return new SchedulerImpl(name, service);
    }

    @AllArgsConstructor
    public static final class SchedulerImpl implements Scheduler {
        private final String name;
        private final ScheduledExecutorService executorService;


        @Override
        public <T> AsyncTask<T> runTask(@NotNull Supplier<? extends T> action) {
            return AsyncTaskFactory.create(this.name, this.executorService, action);
        }

        @Override
        public <T> AsyncTask<T> runTaskLater(@NonNull Supplier<? extends T> action, long time, @NotNull TimeUnit timeUnit) {
            return AsyncTaskFactory.create(this.name, this.executorService, action, time, timeUnit);
        }

        @Override
        public <T> AsyncTask<T> runTaskTimer(@NotNull Function<Integer, ? extends T> action, long time, @NotNull TimeUnit timeUnit) {
            return null;
        }
    }
}
