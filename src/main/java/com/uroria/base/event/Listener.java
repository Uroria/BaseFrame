package com.uroria.base.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PROTECTED)
public abstract class Listener<E> {
    private final Class<E> type;
    private @Getter final int priority;

    public abstract void onEvent(E event);
}
