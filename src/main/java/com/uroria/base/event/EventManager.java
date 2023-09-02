package com.uroria.base.event;

import lombok.NonNull;

public interface EventManager {

    <E> void subscribe(@NonNull Listener<E> listener);

    void unsubscribe(@NonNull Class<Listener<?>> listenerClass);

    void unsubscribeAll();

    <E> E call(E event);

    void callAndForget(Object object);
}
