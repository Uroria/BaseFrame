package com.uroria.base.configs;

import com.google.gson.JsonSyntaxException;
import com.uroria.base.lang.Language;
import com.uroria.fastconfig.Json;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@UtilityClass
public class TranslationConfigurations {
    private final Object2ObjectMap<Language, Json> configs = new Object2ObjectArrayMap<>();
    private final Object2ObjectMap<Language, Json> internalConfigs = new Object2ObjectArrayMap<>();

    static {
        reload();
    }

    public synchronized void reload() {
        for (Language lang : GeneralConfiguration.getAvailableLanguages()) {
            Json config = configs.get(lang);
            if (config == null) {
                config = new Json("lang-" + lang, Configurations.DEFAULT_PATH + "lang");
                configs.put(lang, config);
            }

            Json internalConfig = internalConfigs.get(lang);
            if (internalConfig == null) {
                internalConfig = new Json("lang-" + lang, Configurations.INTERNAL_PATH + "lang");
                internalConfigs.put(lang, internalConfig);
            }
        }

        configs.values().forEach(Json::reload);
        internalConfigs.values().forEach(Json::reload);
    }

    public @NotNull String getTranslation(@Nullable String key, @Nullable Language lang) {
        if (key == null) return "nil";
        if (lang == null) return key + "-nil";
        try {
            Json config = getConfig(lang, key);
            key = key.substring(2);
            if (config == null) return key + "-" + lang;
            return config.getOrSetDefault(key, key);
        } catch (Exception exception) {
            throw new JsonSyntaxException("Clashing translation with " + key + " for " + lang, exception);
        }
    }

    public @NotNull List<String> getTranslations(@Nullable String key, @Nullable Language lang) {
        if (key == null) return ObjectLists.singleton("nil");
        if (lang == null) return ObjectLists.singleton(key + "-nil");
        try {
            Json config = getConfig(lang, key);
            key = key.substring(2);
            if (config == null) return ObjectLists.singleton(key + "-" + lang);
            return config.getOrSetDefault(key, ObjectLists.singleton(key));
        } catch (Exception exception) {
            throw new JsonSyntaxException("Clashing translation list with " + key + " for " + lang, exception);
        }
    }

    private @Nullable Json getConfig(@NonNull Language lang, @NonNull String key) {
        if (key.startsWith("..")) {
            Json config = internalConfigs.get(lang);
            return config != null ? config : internalConfigs.values().stream().findAny().orElse(null);
        }
        Json config = configs.get(lang);
        return config != null ? config : configs.values().stream().findAny().orElse(null);
    }
}
