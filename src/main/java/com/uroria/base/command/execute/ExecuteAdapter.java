package com.uroria.base.command.execute;

import com.uroria.base.command.Commander;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public abstract class ExecuteAdapter {
    private final String name;
    private final ObjectSet<ExecuteAdapter> children;

    public ExecuteAdapter(@NonNull String name, @NonNull ExecuteAdapter... children) {
        this.name = name;
        this.children = new ObjectArraySet<>(List.of(children));
    }

    public void addChild(@NonNull ExecuteAdapter adapter) {
        synchronized (children) {
            this.children.add(adapter);
        }
    }

    public void removeChild(@NonNull ExecuteAdapter adapter) {
        synchronized (children) {
            this.children.remove(adapter);
        }
    }

    protected abstract boolean execute(@NonNull Commander commander, @NonNull Map<String, String> arguments);

    public final boolean execute(@NonNull Commander commander,
                              @NonNull String[] args,
                              int index,
                              @NonNull Map<String, String> arguments) {
        int length = args.length;
        if (length == 0) {
            if (index == 0) {
                execute(commander, arguments);
            }
            return true;
        }
        if (index < length) {
            String current = args[index];
            arguments.put(this.name, current);
        }

        boolean finish = execute(commander, arguments);
        if (finish) return true;

        for (ExecuteAdapter child : this.children) {
            if (child.execute(commander, args, index + 1, arguments)) {
                return true;
            }
        }
        return false;
    }
}
