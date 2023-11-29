package com.uroria.base.command;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public abstract non-sealed class RootCommand extends Command {

    public RootCommand(@NonNull String name, ChildCommand @NonNull [] children, @NonNull String permission, @NotNull @NonNull String... names) {
        super(name, children, permission, names);
    }

    public RootCommand(@NonNull String name, @NonNull Collection<ChildCommand> children, @NonNull String permission, @NonNull Collection<String> names) {
        super(name, children, permission, names);
    }
}
