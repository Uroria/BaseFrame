package com.uroria.base.command.execute;

import com.uroria.base.command.Commander;
import lombok.NonNull;

import java.util.Map;

public final class DefaultExecuteAdapter extends ExecuteAdapter {
    public DefaultExecuteAdapter() {
        super("default");
    }

    @Override
    protected boolean execute(@NonNull Commander commander, @NonNull Map<String, String> arguments) {
        return true;
    }
}
