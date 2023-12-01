package com.uroria.base.command;

import com.uroria.base.command.execute.ExecuteAdapter;
import com.uroria.base.command.offer.OfferAdapter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class Command {
    @Getter protected final String name;
    @Getter protected final String permission;
    protected final List<String> aliases;
    @Getter
    private final ExecuteAdapter executeAdapter;
    @Getter
    private final OfferAdapter offerAdapter;
    private final Collection<Command> children;

    public Command(@NonNull String name, @NonNull String permission, @NonNull List<String> aliases, Command... children) {
        this.name = name;
        this.permission = permission;
        this.aliases = aliases;
        this.children = new ObjectArrayList<>(children);
        this.executeAdapter = new ExecuteAdapter(name) {
            @Override
            protected boolean execute(@NonNull Commander commander, @NonNull String[] args, @NonNull Map<String, String> arguments) {
                return getSelf().execute(commander, args, arguments);
            }
        };
        this.offerAdapter = new OfferAdapter() {
            @Override
            protected void offer(@NonNull Commander commander, @NonNull List<String> offers, @Nullable String last, @Nullable String current) {
                getSelf().offer(commander, offers, last, current);
            }
        };
        setup();
    }

    private void setup() {
        for (Command child : this.children) {
            executeAdapter.addChild(child.executeAdapter);
            offerAdapter.addChild(child.offerAdapter);
        }
    }

    public void addChild(@NonNull Command child) {
        synchronized (children) {
            executeAdapter.addChild(child.executeAdapter);
            offerAdapter.addChild(child.offerAdapter);
            this.children.add(child);
        }
    }

    public void removeChild(@NonNull Command child) {
        synchronized (children) {
            executeAdapter.removeChild(child.executeAdapter);
            offerAdapter.removeChild(child.offerAdapter);
            this.children.remove(child);
        }
    }

    /**
     * @return If true, no child commands will be executed.
     */
    protected abstract boolean execute(@NonNull Commander commander,
                                       @NonNull String[] args,
                                       @NonNull Map<String, String> arguments);

    protected abstract void offer(@NonNull Commander commander,
                                  @NonNull List<String> offers,
                                  @Nullable String last,
                                  @Nullable String current);

    private Command getSelf() {
        return this;
    }
}
