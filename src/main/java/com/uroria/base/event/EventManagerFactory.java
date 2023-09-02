package com.uroria.base.event;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public final class EventManagerFactory {

    public static EventManager create(@NonNull String name) {
        return new EventManagerImpl(name, new Object2ObjectArrayMap<>());
    }

    public static EventManager create(@NonNull String name, @NonNull Object2ObjectMap<String, ObjectList<Listener<?>>> listeners) {
        return new EventManagerImpl(name, listeners);
    }

    private static final class EventManagerImpl implements EventManager {
        private final Object2ObjectMap<String, ObjectList<Listener<?>>> listeners;
        private final Logger logger;

        public EventManagerImpl(String name, Object2ObjectMap<String, ObjectList<Listener<?>>> listeners) {
            this.listeners = listeners;
            this.logger = LoggerFactory.getLogger(name);
        }

        @Override
        public <E> void subscribe(@NonNull Listener<E> listener) {
            String name = listener.getType().getName();
            ObjectList<Listener<?>> listeners = this.listeners.get(name);
            if (listeners == null) {
                listeners = new ObjectArrayList<>();
                this.listeners.put(name, listeners);
            }
            listeners.add(listener);
        }

        @Override
        public void unsubscribe(@NonNull Class<Listener<?>> listenerClass) {
            for (ObjectList<Listener<?>> listeners : this.listeners.values()) {
                listeners.removeIf(listener -> listener.getClass().equals(listenerClass));
            }
        }

        @Override
        public void unsubscribeAll() {
            this.listeners.clear();
        }

        @Override
        public <E> E call(@NonNull E event) {
            ObjectList<Listener<?>> listeners = this.listeners.get(event.getClass().getName());
            if (listeners == null) return event;
            for (Listener<?> listener : listeners) {
                try {
                    Listener<E> correctListener = (Listener<E>) listener;
                    correctListener.onEvent(event);
                } catch (Exception exception) {
                    this.logger.warn("Cannot call listener " + listener.getClass().getName(), exception);
                }
            }
            return event;
        }

        @Override
        public void callAndForget(Object object) {
            CompletableFuture.runAsync(() -> {
                call(object);
            });
        }
    }
}
