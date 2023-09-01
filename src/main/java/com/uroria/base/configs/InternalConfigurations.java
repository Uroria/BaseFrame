package com.uroria.base.configs;

import com.uroria.fastconfig.Json;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

@UtilityClass
public class InternalConfigurations {

    public Json getGeneral() {
        return GeneralConfiguration.getInternal();
    }

    public Json create(@NonNull String name) {
        return create(name, null);
    }

    public Json create(@NonNull String name, @Nullable InputStream template) {
        return new Json(name, Configurations.INTERNAL_PATH, template);
    }

}
