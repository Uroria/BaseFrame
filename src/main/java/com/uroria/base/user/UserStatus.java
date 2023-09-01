package com.uroria.base.user;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public enum UserStatus {
    INVISIBLE(0),
    ONLINE(1),
    DO_NOT_DISTURB(2),
    LIVE(3),
    PLAYING(4);

    public static final UserStatus DEFAULT = INVISIBLE;

    private final int code;

    public static @NotNull UserStatus parseStatus(@Nullable String input) {
        if (input == null) return DEFAULT;
        for (UserStatus status : values()) {
            if (status.toString().equalsIgnoreCase(input)) return status;
        }
        return DEFAULT;
    }

    public static @NotNull UserStatus fromCode(int code) {
        for (UserStatus status : values()) {
            if (status.code == code) return status;
        }
        return DEFAULT;
    }

    public int toCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
