package com.uroria.base.property;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import lombok.NonNull;

import java.util.Optional;

public interface PropertyObject {
    void setProperty(@NonNull String key, @NonNull String value);

    void setProperty(@NonNull String key, boolean value);

    void setProperty(@NonNull String key, int value);

    void setProperty(@NonNull String key, long value);

    void unsetProperty(@NonNull String key);

    Optional<String> getPropertyString(@NonNull String key);

    Optional<Integer> getPropertyInt(@NonNull String key);

    boolean getPropertyBoolean(@NonNull String key);

    Optional<Long> getPropertyLong(@NonNull String key);

    Object2ObjectMap<String, Object> getProperties();

    void setProperties(@NonNull Object2ObjectMap<String, Object> properties);
}
