package com.uroria.base.user;

import com.uroria.base.lang.Language;
import com.uroria.base.permission.PermState;
import com.uroria.base.property.PropertyObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface UroriaUser extends PropertyObject {

    boolean hasPermission(@Nullable String node);

    @NotNull PermState getPermissionState(@Nullable String node);

    @NotNull String getUsername();

    @NotNull UUID getUniqueId();

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
