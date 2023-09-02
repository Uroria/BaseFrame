package com.uroria.base.sentry;

import com.uroria.base.configs.GeneralConfiguration;
import io.sentry.Sentry;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SentryInstance {
    private final String dsn = GeneralConfiguration.getConfig().getString("sentryDsn");

    public void initialize() {
        Sentry.init(option -> {
            option.setDsn(dsn);
            option.setTracesSampleRate(1.0);
        });
    }
}
