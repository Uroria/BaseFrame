package com.uroria.base.lang;

import com.uroria.base.configs.TranslationConfigurations;
import com.uroria.base.user.UroriaUser;
import com.uroria.base.utils.ComponentUtils;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Arrays;

@UtilityClass
public class Translation {

    public Component component(Language language, String key, TagResolver resolver) {
        return ComponentUtils.deserialize(TranslationConfigurations.getTranslation(key, language), resolver);
    }

    public Component component(Language language, String key) {
        return component(language, key, null);
    }

    public Component component(UroriaUser user, String key, TagResolver resolver) {
        return component(user.getLanguage(), key, resolver);
    }

    public Component component(UroriaUser user, String key) {
        return component(user.getLanguage(), key, null);
    }

    public String string(Language language, String key, TagResolver resolver) {
        return ComponentUtils.serialize(component(language, key, resolver));
    }

    public String string(Language language, String key) {
        return string(language, key, null);
    }

    public String string(UroriaUser user, String key, TagResolver resolver) {
        return string(user.getLanguage(), key, resolver);
    }

    public String string(UroriaUser user, String key) {
        return string(user.getLanguage(), key, null);
    }

    public Component[] components(Language language, String key, TagResolver resolver) {
        return TranslationConfigurations.getTranslations(key, language).stream().map(s -> ComponentUtils.deserialize(s, resolver)).toArray(Component[]::new);
    }

    public Component[] components(Language language, String key) {
        return components(language, key, null);
    }

    public Component[] components(UroriaUser user, String key, TagResolver resolver) {
        return components(user.getLanguage(), key, resolver);
    }

    public Component[] components(UroriaUser user, String key) {
        return components(user.getLanguage(), key, null);
    }

    public String[] strings(Language language, String key, TagResolver resolver) {
        return Arrays.stream(components(language, key, resolver)).map(ComponentUtils::serialize).toArray(String[]::new);
    }

    public String[] strings(Language language, String key) {
        return strings(language, key, null);
    }

    public String[] strings(UroriaUser user, String key, TagResolver resolver) {
        return strings(user.getLanguage(), key, resolver);
    }

    public String[] strings(UroriaUser user, String key) {
        return strings(user.getLanguage(), key);
    }
}
