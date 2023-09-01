package com.uroria.base.io;

import lombok.NonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;

public class InsaneByteArrayInputStream extends ObjectInputStream implements AutoCloseable {

    private final ByteArrayInputStream inputBuffer;

    private InsaneByteArrayInputStream(ByteArrayInputStream inputBuffer) throws IOException {
        super(inputBuffer);
        this.inputBuffer = inputBuffer;
    }

    public InsaneByteArrayInputStream(byte[] data) throws IOException {
        this(new ByteArrayInputStream(data));
    }

    public <T> Optional<T> readSafeObject(@NonNull Class<T> clazz) throws IOException, ClassNotFoundException {
        if (readBoolean()) //noinspection unchecked
            return Optional.of((T) readObject());
        return Optional.empty();
    }

    @Override
    public void close() throws IOException {
        this.inputBuffer.close();
        super.close();
    }
}
