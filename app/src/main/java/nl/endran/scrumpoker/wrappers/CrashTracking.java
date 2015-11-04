/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.wrappers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;

import nl.endran.scrumpoker.BuildConfig;

public class CrashTracking {

    @NonNull
    private FabricFactory fabricFactory;

    public CrashTracking(@NonNull final FabricFactory fabricFactory) {
        this.fabricFactory = fabricFactory;
    }

    public void start(@NonNull final Context context) {
        if (shouldTrack()) {
            fabricFactory.create(context, new Crashlytics());
        }
    }

    private boolean shouldTrack() {
        return BuildConfig.VERSION_NAME.contains("master");
    }
}
