package com.uroria.base.configs;

import com.uroria.annotations.markers.Warning;
import com.uroria.fastconfig.Json;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

@UtilityClass
public class InternalConfigurations {

    @Warning(message = "You won't have access for editing the general configuration file without admin access")
    public Json getGeneral() {
        return GeneralConfiguration.getInternal();
    }

    @SuppressWarnings("WarningMarkers")
    @Warning(message = "You won't have access to the configuration file without admin access")
    public Json create(@NonNull String name) {
        return create(name, null);
    }

    @Warning(message = "You won't have access to the configuration file without admin access")
    public Json create(@NonNull String name, @Nullable InputStream template) {
        return new Json(name, Configurations.INTERNAL_PATH, template);
    }
}
