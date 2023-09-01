package com.uroria.base.event;

import lombok.NonNull;

public interface EventManager {

    void subscribe(@NonNull Listener<?> listener);

    void unsubscribe(@NonNull Class<Listener<?>> listenerClass);

    void unsubscribeAll();

    <E> E call(E event);

    void callAndForget(Object object);
}
