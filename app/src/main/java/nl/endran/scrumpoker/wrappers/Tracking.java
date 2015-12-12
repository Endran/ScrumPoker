/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.wrappers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.ShareEvent;

import nl.endran.scrumpoker.BuildConfig;

public class Tracking {

    @NonNull
    private FabricFactory fabricFactory;

    public Tracking(@NonNull final FabricFactory fabricFactory) {
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

    public void logContentView(ContentViewEvent event) {
        if (shouldTrack()) {
            fabricFactory.getAnswers().logContentView(event);
        }
    }

    public void logCustom(final CustomEvent event) {
        if (shouldTrack()) {
            fabricFactory.getAnswers().logCustom(event);
        }
    }

    public void logShare(final ShareEvent event) {
        if (shouldTrack()) {
            fabricFactory.getAnswers().logShare(event);
        }
    }
}
