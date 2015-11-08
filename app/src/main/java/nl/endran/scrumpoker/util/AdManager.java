/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.util;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class AdManager {

    public interface Listener {
        void onShouldShowAds(boolean shouldShowAds);
    }

    public void checkForAds(@NonNull final Handler handler, @NonNull final String adUrl, @NonNull final Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean shouldShowAds = false;

                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    new URL(adUrl).openStream()));
                    String line = in.readLine();
                    in.close();

                    AdLevel[] adLevelArray = new Gson().fromJson(line, AdLevel[].class);
                    AdLevel adLevel = adLevelArray[0];

                    shouldShowAds = adLevel.getAdLevel() > 0;
                } catch (Exception e) {
                    Log.e("AdManager", "An error occurred while checking for ads", e);
                }

                informListener(shouldShowAds, handler, listener);
            }
        }).start();
    }

    private void informListener(final boolean shouldShowAds, final @NonNull Handler handler, final @NonNull Listener listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onShouldShowAds(shouldShowAds);
            }
        });
    }
}
