package com.uroria.base.permission;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UroriaPermObject {

    boolean hasPermission(@Nullable String node);

    @NotNull PermState getPermissionState(@Nullable String node);

    void setPermission(@NonNull String node, boolean value);

    void setPermission(@NonNull String node, @NonNull PermState state);

    void unsetPermission(@NonNull String node);


}
