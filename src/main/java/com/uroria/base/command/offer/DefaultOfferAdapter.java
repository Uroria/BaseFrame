package com.uroria.base.command.offer;

import com.uroria.base.command.Commander;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class DefaultOfferAdapter extends OfferAdapter {

    public DefaultOfferAdapter(OfferAdapter... children) {
        super(children);
    }

    @Override
    protected void offer(@NonNull Commander commander, @NonNull List<String> offers, @Nullable String last, @Nullable String current) {

    }
}
