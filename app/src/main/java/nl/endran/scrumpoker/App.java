/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;

import io.fabric.sdk.android.Fabric;

public class App extends Application {

    private Analytics analytics;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.VERSION_NAME.contains("master")) {
            Fabric.with(this, new Crashlytics());
        }

        analytics = new Analytics(GoogleAnalytics.getInstance(this));

//        initCalligraphy();
    }

//    protected void initCalligraphy() {
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                        .setDefaultFontPath(getString(R.string.font_sans_tall_x))
//                        .setFontAttrId(R.attr.fontPath)
//                        .build()
//        );
//    }


    public Analytics getAnalytics() {
        return analytics;
    }
}
