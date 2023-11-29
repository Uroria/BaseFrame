package com.uroria.base.command;

import com.uroria.base.lang.Languageable;

public interface Commander extends Languageable {

    boolean hasPermission(String permission);

}
