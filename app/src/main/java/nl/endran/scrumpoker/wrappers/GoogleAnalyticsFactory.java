/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.wrappers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.analytics.GoogleAnalytics;

public class GoogleAnalyticsFactory {

    public GoogleAnalytics create(@NonNull final Context context) {
        return GoogleAnalytics.getInstance(context);
    }
}
