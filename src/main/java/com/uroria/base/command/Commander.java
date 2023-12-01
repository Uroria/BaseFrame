package com.uroria.base.command;

import com.uroria.base.lang.Languageable;
import net.kyori.adventure.text.Component;

public interface Commander extends Languageable {

    boolean hasPermission(String permission);

    void sendMessage(Component component);
}
