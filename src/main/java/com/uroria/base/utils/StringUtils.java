package com.uroria.base.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class StringUtils {

    public void addIfStarts(@NonNull List<String> list, String start, Object other) {
        if (other == null) return;
        if (start == null) {
            list.add(other.toString());
            return;
        }
        String string = other.toString();
        if (string.startsWith(start)) list.add(string);
    }

    public void addIfStarts(@NonNull List<String> list, String start, Collection<Object> others) {
        if (others == null) return;
        if (start == null) {
            list.addAll(others.stream().map(Object::toString).collect(Collectors.toUnmodifiableSet()));
            return;
        }
        for (Object o : others) {
            addIfStarts(list, start, o);
        }
    }

    public void addIfStarts(@NonNull List<String> list, String start, Object... others) {
        if (others == null) return;
        if (start == null) {
            list.addAll(Arrays.stream(others).map(Object::toString).collect(Collectors.toUnmodifiableSet()));
            return;
        }
        for (Object o : others) {
            addIfStarts(list, start, o);
        }
    }

    public @Nullable Boolean toBooleanOrNull(@NonNull String input) {
        try {
            return Boolean.parseBoolean(input);
        } catch (Exception exception) {
            return null;
        }
    }

    public boolean toBooleanOrElse(@NonNull String input, boolean def) {
        try {
            return Boolean.parseBoolean(input);
        } catch (Exception exception) {
            return def;
        }
    }

    public @Nullable Byte toByteOrNull(@NonNull String input) {
        try {
            return Byte.parseByte(input);
        } catch (Exception exception) {
            return null;
        }
    }

    public byte toByteOrElse(@NonNull String input, byte def) {
        try {
            return Byte.parseByte(input);
        } catch (Exception exception) {
            return def;
        }
    }

    public @Nullable Short toShortOrNull(@NonNull String input) {
        try {
            return Short.parseShort(input);
        } catch (Exception exception) {
            return null;
        }
    }

    public short toShortOrElse(@NonNull String input, short def) {
        try {
            return Short.parseShort(input);
        } catch (Exception exception) {
            return def;
        }
    }

    public @Nullable Integer toIntOrNull(@NonNull String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception exception) {
            return null;
        }
    }

    public int toIntOrElse(@NonNull String input, int def) {
        try {
            return Integer.parseInt(input);
        } catch (Exception exception) {
            return def;
        }
    }

    public @Nullable Long toLongOrNull(@NonNull String input) {
        try {
            return Long.parseLong(input);
        } catch (Exception exception) {
            return null;
        }
    }

    public long toLongOrElse(@NonNull String input, long def) {
        try {
            return Long.parseLong(input);
        } catch (Exception exception) {
            return def;
        }
    }

    public @Nullable Float toFloatOrNull(@NonNull String input) {
        try {
            return Float.parseFloat(input);
        } catch (Exception exception) {
            return null;
        }
    }

    public float toFloatOrElse(@NonNull String input, float def) {
        try {
            return Float.parseFloat(input);
        } catch (Exception exception) {
            return def;
        }
    }

    public @Nullable Double toDoubleOrNull(@NonNull String input) {
        try {
            return Double.parseDouble(input);
        } catch (Exception exception) {
            return null;
        }
    }

    public double toDoubleOrElse(@NonNull String input, double def) {
        try {
            return Double.parseDouble(input);
        } catch (Exception exception) {
            return def;
        }
    }
}
