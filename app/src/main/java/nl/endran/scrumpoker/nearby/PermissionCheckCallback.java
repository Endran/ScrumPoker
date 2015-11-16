/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.nearby;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class PermissionCheckCallback implements ResultCallback<Status> {

    public interface Listener {
        void onPermissionAllowed();

        void onPermissionNotAllowed(@Nullable final Status status);
    }

    private Listener listener;

    public PermissionCheckCallback(@NonNull final Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onResult(final Status status) {
        if (status.isSuccess()) {
            listener.onPermissionAllowed();
        } else {
            listener.onPermissionNotAllowed(status);
        }
    }
}