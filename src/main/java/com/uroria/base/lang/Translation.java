package com.uroria.base.lang;

import com.uroria.base.configs.TranslationConfigurations;
import com.uroria.base.utils.ComponentUtils;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Arrays;

@UtilityClass
public class Translation {

    public Component component(Language language, String key, TagResolver... resolver) {
        return ComponentUtils.deserialize(TranslationConfigurations.getTranslation(key, language), resolver);
    }

    public Component component(Languageable user, String key, TagResolver... resolver) {
        return component(user.getLanguage(), key, resolver);
    }

    public Component component(Languageable user, String key) {
        return component(user.getLanguage(), key);
    }

    public String string(Language language, String key, TagResolver... resolver) {
        return ComponentUtils.serialize(component(language, key, resolver));
    }

    public String string(Languageable user, String key, TagResolver... resolver) {
        return string(user.getLanguage(), key, resolver);
    }

    public String string(Languageable user, String key) {
        return string(user.getLanguage(), key);
    }

    public Component[] components(Language language, String key, TagResolver... resolver) {
        return TranslationConfigurations.getTranslations(key, language).stream().map(s -> ComponentUtils.deserialize(s, resolver)).toArray(Component[]::new);
    }

    public Component[] components(Languageable user, String key, TagResolver... resolver) {
        return components(user.getLanguage(), key, resolver);
    }

    public Component[] components(Languageable user, String key) {
        return components(user.getLanguage(), key);
    }

    public String[] strings(Language language, String key, TagResolver... resolver) {
        return Arrays.stream(components(language, key, resolver)).map(ComponentUtils::serialize).toArray(String[]::new);
    }

    public String[] strings(Languageable user, String key, TagResolver... resolver) {
        return strings(user.getLanguage(), key, resolver);
    }

    public String[] strings(Languageable user, String key) {
        return strings(user.getLanguage(), key);
    }
}
