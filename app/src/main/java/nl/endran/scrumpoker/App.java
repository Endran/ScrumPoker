/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.app.Application;

import java.util.UUID;

import nl.endran.scrumpoker.wrappers.Analytics;
import nl.endran.scrumpoker.wrappers.Tracking;
import nl.endran.scrumpoker.wrappers.FabricFactory;
import nl.endran.scrumpoker.wrappers.GoogleAnalyticsFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {

    private Analytics analytics;
    private Tracking tracking;

    @Override
    public void onCreate() {
        super.onCreate();

        initCrashTracking();
        initAnalytics();
        initCalligraphy();

        Preferences preferences = new Preferences(getApplicationContext());
        if (preferences.getUniqueId().isEmpty()) {
            preferences.setUniqueId(UUID.randomUUID().toString());
        }
    }

    private void initCrashTracking() {
        tracking = new Tracking(new FabricFactory());
        tracking.start(this);
    }

    private void initAnalytics() {
        analytics = new Analytics(this, new GoogleAnalyticsFactory());
    }

    protected void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(getString(R.string.font_sans_tall_x))
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    public Analytics getAnalytics() {
        return analytics;
    }

    public Tracking getTracking() {
        return tracking;
    }
}
