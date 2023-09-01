package com.uroria.base.permission;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface UroriaPermHolder extends UroriaPermObject {

    @NotNull UUID getUniqueId();
}
