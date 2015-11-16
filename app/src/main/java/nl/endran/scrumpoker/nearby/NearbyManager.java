/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.nearby;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import nl.endran.scrumpoker.Preferences;

public class NearbyManager {

    public enum State {
        SELECTING, READY, SHOWING
    }

    public interface Listener {
        void onEverybodyReady();
    }

    @NonNull
    private Preferences preferences;

    @NonNull
    private final NearbyHelper nearbyHelper;
    private Handler handler;

    @NonNull
    private final Map<String, State> stateMap = new HashMap<>();

    @Nullable
    private Listener listener;

    public NearbyManager(@NonNull final Preferences preferences,
                         @NonNull final NearbyHelper nearbyHelper,
                         @NonNull final Handler handler) {
        this.preferences = preferences;
        this.nearbyHelper = nearbyHelper;
        this.handler = handler;
    }

    public void start(Listener listener) {
        this.listener = listener;

        nearbyHelper.subscribe(new MessageListener() {
            @Override
            public void onFound(final Message message) {
                String messageString = getMessageString(message);
                String[] split = messageString.split(":");
                if (split.length == 2) {
                    String id = split[0];
                    State state = State.valueOf(split[1]);
                    stateMap.put(id, state);

                    analyseMapDelayed();
                }
            }
        });
    }

    @NonNull
    private String getMessageString(final Message message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new String(message.getContent(), StandardCharsets.UTF_8);
        } else {
            try {
                return new String(message.getContent(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
    }

    private void analyseMapDelayed() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                analyseMap();
            }
        }, 1000);
    }

    private void analyseMap() {
        boolean allReady = stateMap.size() > 1;
        for (State state : stateMap.values()) {
            allReady &= !state.equals(State.SELECTING);
        }

        if (allReady && listener != null) {
            listener.onEverybodyReady();
            stateMap.clear();
        }
    }

    public void stop() {
        this.listener = null;
    }

    public void setState(@NonNull final State state) {
        if (nearbyHelper.isNearbyAllowed() && preferences.shouldUseNearby() && nearbyHelper.isConnected()) {
            nearbyHelper.unPublishAll();
            String uniqueId = preferences.getUniqueId();
            stateMap.put(uniqueId, state);
            byte[] messageBytes = String.format("%s:%s", uniqueId, state.name()).getBytes();
            nearbyHelper.publishMessage(messageBytes);
            analyseMapDelayed();
        }
    }
}
