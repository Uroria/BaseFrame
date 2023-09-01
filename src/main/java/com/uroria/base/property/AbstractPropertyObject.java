package com.uroria.base.property;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractPropertyObject implements Serializable, PropertyObject {
    @Serial private static final long serialVersionUID = 1;

    protected final Object2ObjectMap<String, Object> properties;

    public AbstractPropertyObject() {
        this.properties = new Object2ObjectArrayMap<>();
    }

    @Override
    public final void setProperty(@NonNull String key, @NonNull String value) {
        this.properties.put(key, value);
    }

    @Override
    public final void setProperty(@NonNull String key, boolean value) {
        this.properties.put(key, value);
    }

    @Override
    public final void setProperty(@NonNull String key, int value) {
        this.properties.put(key, value);
    }

    @Override
    public final void setProperty(@NonNull String key, long value) {
        this.properties.put(key, value);
    }

    @Override
    public final void unsetProperty(@NonNull String key) {
        this.properties.remove(key);
    }

    @Override
    public final Optional<String> getPropertyString(@NonNull String key) {
        Object o = this.properties.get(key);
        if (o == null) return Optional.empty();
        return Optional.of((String) o);
    }

    @Override
    public final Optional<Integer> getPropertyInt(@NonNull String key) {
        Object o = this.properties.getOrDefault(key, 0);
        if (o == null) return Optional.empty();
        if (o instanceof Integer i) {
            return Optional.of(i);
        }
        if (o instanceof Float f) {
            int i = f.intValue();
            return Optional.of(i);
        }
        if (o instanceof Short s) {
            int i = s;
            return Optional.of(i);
        }
        return Optional.empty();
    }

    @Override
    public final boolean getPropertyBoolean(@NonNull String key) {
        return (boolean) this.properties.getOrDefault(key, false);
    }

    @Override
    public final Optional<Long> getPropertyLong(@NonNull String key) {
        Object o = this.properties.get(key);
        if (o == null) return Optional.empty();
        if (o instanceof Long l) {
            return Optional.of(l);
        }
        if (o instanceof Integer i) {
            long l = i;
            return Optional.of(l);
        }
        if (o instanceof Double d) {
            long l = d.longValue();
            return Optional.of(l);
        }
        if (o instanceof Float f) {
            long l = f.longValue();
            return Optional.of(l);
        }
        if (o instanceof Short s) {
            long l = s;
            return Optional.of(l);
        }
        return Optional.empty();
    }

    @Override
    public final Object2ObjectMap<String, Object> getProperties() {
        return Object2ObjectMaps.unmodifiable(this.properties);
    }

    @Override
    public final void setProperties(@NonNull Object2ObjectMap<String, Object> properties) {
        synchronized (this.properties) {
            this.properties.keySet().forEach(key -> {
                if (!properties.containsKey(key)) this.properties.remove(key);
            });
            this.properties.putAll(properties);
        }
    }
}
