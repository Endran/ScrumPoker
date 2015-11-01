/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */
public class Analytics {

    @Nullable
    private final Tracker tracker;

    public Analytics(@NonNull final Context context, @NonNull final GoogleAnalyticsFactory analyticsFactory) {
        if (shouldInitTracker()) {
            GoogleAnalytics googleAnalytics = analyticsFactory.create(context);
            tracker = googleAnalytics.newTracker(R.xml.global_tracker);
            tracker.enableAdvertisingIdCollection(true);
        } else {
            tracker = null;
        }
    }

    private boolean shouldInitTracker() {
        return BuildConfig.VERSION_NAME.contains("master");
    }

    public void trackPage(@NonNull String pageName) {
        if (tracker != null) {
            tracker.setScreenName("Image~" + pageName);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    public void trackEvent(@NonNull String eventName) {
        if (tracker != null) {
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction(eventName)
                    .build());
        }
    }
}
