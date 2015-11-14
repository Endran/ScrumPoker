/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.nearby;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import nl.endran.scrumpoker.Preferences;
import nl.endran.scrumpoker.nearby.PermissionCheckCallback;

public class NearbyHelper {

    private boolean isConnected = false;

    private GoogleApiClient googleApiClient;
    private Preferences preferences;

    private SparseArray<Message> messageArray = new SparseArray<>();
    private SparseArray<MessageListener> subscriberArray = new SparseArray<>();

    private int messageCounter = 0;
    private int subscriberCounter = 0;

    public NearbyHelper(@NonNull final Context context, @NonNull final Preferences preferences) {
        this.preferences = preferences;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(final Bundle bundle) {
                        isConnected = true;
                    }

                    @Override
                    public void onConnectionSuspended(final int i) {
                        isConnected = false;
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(final ConnectionResult connectionResult) {
                        isConnected = false;
                    }
                })
                .build();
    }

    public void start() {
        if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    public void stop() {
        if (googleApiClient.isConnected()) {
            unPublishAll();
            unSubscribeAll();
        }
        googleApiClient.disconnect();
    }

    public void verifyPermission(final PermissionCheckCallback.Listener listener) {
        if (isConnected) {
            Nearby.Messages.getPermissionStatus(googleApiClient).setResultCallback(new PermissionCheckCallback(new PermissionCheckCallback.Listener() {
                @Override
                public void onPermissionAllowed() {
                    preferences.setNearbyAllowed(true);
                    listener.onPermissionAllowed();
                }

                @Override
                public void onPermissionNotAllowed(final Status status) {
                    preferences.setNearbyAllowed(false);
                    listener.onPermissionNotAllowed(status);
                }
            }));
        } else {
            listener.onPermissionNotAllowed(null);
        }
    }

    public boolean isNearbyAllowed() {
        return preferences.isNearbyAllowed();
    }

    public boolean isConnected() {
        return isConnected;
    }

    private void unPublishAll() {
        while (messageArray.size() > 0) {
            Message message = messageArray.valueAt(0);
            messageArray.removeAt(0);
            Nearby.Messages.unpublish(googleApiClient, message);
        }
    }

    private void unSubscribeAll() {
        while (subscriberArray.size() > 0) {
            MessageListener subscriber = subscriberArray.valueAt(0);
            subscriberArray.removeAt(0);
            Nearby.Messages.unsubscribe(googleApiClient, subscriber);
        }
    }

    public int publishMessage(byte[] messageBytes) {
        int index = messageCounter;
        messageCounter++;

        Message message = new Message(messageBytes);
        messageArray.put(index, message);

        Nearby.Messages.publish(googleApiClient, message);

        return index;
    }

    public void unPublishMessage(int messageIndex) {
        Message message = messageArray.get(messageIndex);
        messageArray.remove(messageIndex);

        Nearby.Messages.unpublish(googleApiClient, message);
    }

    public int subscribe(MessageListener subscriber) {
        int index = subscriberCounter;
        subscriberCounter++;

        subscriberArray.put(index, subscriber);

        Nearby.Messages.subscribe(googleApiClient, subscriber);

        return index;
    }

    public void unSubscribe(int subscriberIndex) {
        MessageListener subscriber = subscriberArray.get(subscriberIndex);
        subscriberArray.remove(subscriberIndex);

        Nearby.Messages.unsubscribe(googleApiClient, subscriber);
    }
}
