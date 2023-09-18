package com.uroria.base.property;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
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
    public final String getPropertyStringOrElse(@NonNull String key, String defValue) {
        Object o = this.properties.get(key);
        if (o == null) return defValue;
        return (String) o;
    }

    @Override
    public final int getPropertyIntOrElse(@NonNull String key, int defValue) {
        Object o = this.properties.getOrDefault(key, 0);
        if (o == null) return defValue;
        if (o instanceof Integer i) {
            return i;
        }
        if (o instanceof Float f) {
            return f.intValue();
        }
        if (o instanceof Short s) {
            return (int) s;
        }
        return defValue;
    }


    @Override
    public final boolean getPropertyBooleanOrElse(@NonNull String key, boolean defValue) {
        return (boolean) this.properties.getOrDefault(key, defValue);
    }

    @Override
    public long getPropertyLongOrElse(@NonNull String key, long defValue) {
        Object o = this.properties.get(key);
        if (o == null) return defValue;
        if (o instanceof Long l) {
            return l;
        }
        if (o instanceof Integer i) {
            return (long) i;
        }
        if (o instanceof Double d) {
            return d.longValue();
        }
        if (o instanceof Float f) {
            return f.longValue();
        }
        if (o instanceof Short s) {
            return (long) s;
        }
        return defValue;
    }

    @Override
    public final Object2ObjectMap<String, Object> getProperties() {
        return Object2ObjectMaps.unmodifiable(this.properties);
    }

    @Override
    public final void setProperties(@NonNull Map<String, Object> properties) {
        synchronized (this.properties) {
            this.properties.keySet().forEach(key -> {
                if (!properties.containsKey(key)) this.properties.remove(key);
            });
            this.properties.putAll(properties);
        }
    }
}
