package com.uroria.base.lang;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public enum Language {
    GERMAN("de"),
    ENGLISH("en"),
    FRENCH("fr"),
    LITHUANIAN("lt"),
    SPANISH("sp"),
    RUSSIAN("ru");

    public static final Language DEFAULT = GERMAN;

    private final String tag;

    public static @NotNull Language parseLanguage(@Nullable String input) {
        if (input == null) return DEFAULT;
        for (Language lang : values()) {
            if (lang.toString().equalsIgnoreCase(input)) return lang;
        }
        return DEFAULT;
    }

    public static @NotNull Language fromTag(@Nullable String input) {
        if (input == null) return DEFAULT;
        for (Language lang : values()) {
            if (lang.tag.equalsIgnoreCase(input)) return lang;
        }
        return DEFAULT;
    }

    public @NotNull String toTag() {
        return this.tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
