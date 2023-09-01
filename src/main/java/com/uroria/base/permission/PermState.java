package com.uroria.base.permission;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PermState {
    FALSE(false),
    TRUE(true),
    NOT_SET(false);

    public static final PermState DEFAULT = NOT_SET;

    private final boolean val;

    public boolean asBooleanValue() {
        return val;
    }
}
