package com.uroria.base.command.offer;

import com.uroria.base.command.Commander;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public abstract class OfferAdapter {
    private final ObjectSet<OfferAdapter> children;

    public OfferAdapter(@NonNull OfferAdapter... children) {
        this.children = new ObjectArraySet<>(List.of(children));
    }

    public void addChild(@NonNull OfferAdapter adapter) {
        synchronized (children) {
            this.children.add(adapter);
        }
    }

    public void removeChild(@NonNull OfferAdapter adapter) {
        synchronized (children) {
            this.children.remove(adapter);
        }
    }

    protected abstract void offer(@NonNull Commander commander,
                                  @NonNull List<String> offers,
                                  @Nullable String last,
                                  @Nullable String current);

    public final void offer(@NonNull Commander commander,
                            @NonNull String[] args,
                            int index,
                            @NonNull List<String> offers,
                            @Nullable String last) {
        int length = args.length;
        if (length == 0) {
            if (index == 0) {
                offer(commander, offers, null, null);
            }
            return;
        }
        if (index == (length - 1)) {
            String current = args[index];
            if (length > 1) {
                String lastTyped = args[index - 1];
                offer(commander, offers, lastTyped, current);
                return;
            }
            offer(commander, offers, null, current);
            return;
        }

        if (length > 1) {
            String lastTyped = args[length - 1];
            for (OfferAdapter child : children) {
                child.offer(commander, args, index + 1, offers, lastTyped);
            }
            return;
        }

        for (OfferAdapter child : children) {
            child.offer(commander, args, index + 1, offers, null);
        }
    }

    public Set<OfferAdapter> getChildren() {
        return ObjectSets.unmodifiable(this.children);
    }
}
