package com.uroria.base.configs;

import com.uroria.base.lang.Language;
import com.uroria.fastconfig.Json;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneralConfiguration {
    private @Getter final Json config = Configurations.create("config");
    private final Json internalConfig = new Json("config", "./base");

    static {
        reload();
    }

    private @Getter @Setter boolean offline;
    private @Getter @Setter Language[] availableLanguages;

    public synchronized void reload() {
        config.reload();
        internalConfig.reload();

        offline = internalConfig.getOrSetDefault("offline", false);

        String[] languagesString = internalConfig.getOrSetDefault("languages", new String[]{"de"});
        ObjectList<Language> languages = new ObjectArrayList<>();
        for (String tag : languagesString) {
            languages.add(Language.fromTag(tag));
        }
        availableLanguages = languages.toArray(Language[]::new);
    }

    Json getInternal() {
        return internalConfig;
    }
}
