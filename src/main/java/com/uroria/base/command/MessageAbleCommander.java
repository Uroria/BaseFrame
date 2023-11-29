package com.uroria.base.command;

import com.uroria.base.utils.ComponentUtils;
import net.kyori.adventure.text.Component;

public interface MessageAbleCommander extends Commander {

    void sendMessage(Component text);

    default void sendMessage(String miniMessage) {
        sendMessage(ComponentUtils.deserialize(miniMessage));
    }
}
