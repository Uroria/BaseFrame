package com.uroria.base.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class ComponentUtils {
    public Component deserialize(@Nullable String input) {
        return deserialize(input, null);
    }

    public Component deserialize(@Nullable String input, @Nullable TagResolver tagResolver) {
        if (input == null) return Component.empty();
        if (tagResolver == null) {
            return MiniMessage.miniMessage().deserialize(input);
        }
        return MiniMessage.miniMessage().deserialize(input, tagResolver);
    }

    public String serialize(@Nullable String input) {
        return serialize(input, null);
    }

    public String serialize(@Nullable String input, @Nullable TagResolver resolver) {
        return serialize(deserialize(input, resolver));
    }

    public String serialize(@Nullable Component component) {
        if (component == null) return "";
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }
}
