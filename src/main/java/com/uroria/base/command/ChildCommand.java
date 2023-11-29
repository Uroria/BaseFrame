package com.uroria.base.command;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public abstract non-sealed class ChildCommand extends Command {

    public ChildCommand(@NonNull String name, ChildCommand @NonNull [] children, @NonNull String permission, @NotNull @NonNull String... names) {
        super(name, children, permission, names);
    }

    public ChildCommand(@NonNull String name, @NonNull Collection<ChildCommand> children, @NonNull String permission, @NonNull Collection<String> names) {
        super(name, children, permission, names);
    }
}
