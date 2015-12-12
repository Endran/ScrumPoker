/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import nl.endran.scrumpoker.util.AdManager;
import nl.endran.scrumpoker.wrappers.Analytics;
import nl.endran.scrumpoker.wrappers.Tracking;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {

    protected Tracking tracking;

    @Override
    @CallSuper
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        App application = (App) (getApplication());
        tracking = application.getTracking();

        Analytics analytics = application.getAnalytics();
        analytics.trackPage(getPageName());

        final AdView adView = (AdView) findViewById(R.id.adView);
        AdManager adManager = new AdManager();
        adManager.checkForAds(new Handler(), getString(R.string.ad_url), new AdManager.Listener() {
            @Override
            public void onShouldShowAds(final boolean shouldShowAds) {
                if (shouldShowAds) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                    adView.setVisibility(View.VISIBLE);
                } else {
                    adView.setVisibility(View.GONE);
                }
            }
        });
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract String getPageName();
}
