package com.uroria.base.io;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class InsaneByteArrayOutputStream extends ObjectOutputStream implements AutoCloseable {
    private final ByteArrayOutputStream outputBuffer;

    private InsaneByteArrayOutputStream(ByteArrayOutputStream outputStream) throws IOException {
        super(outputStream);
        this.outputBuffer = outputStream;
    }

    public InsaneByteArrayOutputStream() throws IOException {
        this(new ByteArrayOutputStream());
    }

    public void writeSafeObject(@Nullable Serializable object) throws IOException {
        if (object == null) {
            writeBoolean(false);
            return;
        }
        writeBoolean(true);
        writeObject(object);
    }

    @Override
    public void close() throws IOException {
        this.outputBuffer.close();
        super.close();
    }

    public byte[] toByteArray() {
        return this.outputBuffer.toByteArray();
    }

}
