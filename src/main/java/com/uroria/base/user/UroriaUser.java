package com.uroria.base.user;

import com.uroria.base.lang.Language;
import com.uroria.base.permission.UroriaPermHolder;
import com.uroria.base.property.PropertyObject;
import org.jetbrains.annotations.NotNull;


public interface UroriaUser extends PropertyObject, UroriaPermHolder {

    @NotNull String getUsername();

    @NotNull Language getLanguage();

    boolean isOnline();

    /**
     * Gets the current status of the user.
     * If he's offline {@link UserStatus#INVISIBLE} is returned.
     */
    UserStatus getStatus();

    /**
     * Gets the status of the user set by the user.
     * Ignores if he's online.
     */
    UserStatus getRealStatus();
}
