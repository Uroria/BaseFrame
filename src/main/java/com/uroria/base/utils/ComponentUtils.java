package com.uroria.base.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class ComponentUtils {

    public Component deserialize(String input) {
        return MiniMessage.miniMessage().deserialize(input);
    }

    public Component deserialize(@Nullable String input, TagResolver... tagResolver) {
        if (input == null) return Component.empty();
        return MiniMessage.miniMessage().deserialize(input, tagResolver);
    }

    public String serialize(String input) {
        return serialize(deserialize(input));
    }

    public String serialize(@Nullable String input, TagResolver... resolver) {
        return serialize(deserialize(input, resolver));
    }

    public String serialize(@Nullable Component component) {
        if (component == null) return "";
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }
}
