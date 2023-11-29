package com.uroria.base.command;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract sealed class Command permits ChildCommand, RootCommand {
    protected final String name;
    protected final String[] aliases;
    protected final ChildCommand[] children;
    protected final String permission;

    public Command(@NonNull String name, ChildCommand @NonNull [] children, @NonNull String permission, @NonNull String... aliases) {
        this.name = name;
        this.children = children;
        this.permission = permission;
        this.aliases = aliases;
    }

    public Command(@NonNull String name, @NonNull Collection<ChildCommand> children, @NonNull String permission, @NonNull Collection<String> aliases) {
        this(name, children.toArray(ChildCommand[]::new), permission, aliases.toArray(String[]::new));
    }

    public abstract void execute(Commander commander, @Nullable Command parent);

    public void suggest(Commander commander, List<String> suggestions, @Nullable Command parent) {}

    public final List<String> suggestions(@Nullable Commander commander, @Nullable Command parent) {
        if (commander != null && commander.hasPermission(permission)) {
            ObjectList<String> suggestions = new ObjectArrayList<>();
            for (ChildCommand command : this.children) {
                if (commander.hasPermission(command.permission)) {
                    suggestions.addAll(Arrays.asList(command.aliases));
                    suggestions.add(command.name);
                }
            }
            suggest(commander, suggestions, parent);
            return suggestions;
        }
        return ObjectLists.emptyList();
    }
}
