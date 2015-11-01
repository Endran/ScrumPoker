/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        initCalligraphy();
    }

//    protected void initCalligraphy() {
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                        .setDefaultFontPath(getString(R.string.font_sans_tall_x))
//                        .setFontAttrId(R.attr.fontPath)
//                        .build()
//        );
//    }

    private Tracker tracker;

    public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker(R.xml.global_tracker);
            tracker.enableAdvertisingIdCollection(true);
        }
        return tracker;
    }
}
