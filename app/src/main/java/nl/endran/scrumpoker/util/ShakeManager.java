/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.util;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;

public class ShakeManager {

    public interface Listener {
        void onShake();
    }

    @NonNull
    private final SensorManager sensorManager;

    private Listener listener;
    private float acceleration; // acceleration apart from gravity
    private float accelerationCurrent; // current acceleration including gravity
    private float accelerationLast; // last acceleration including gravity
    private ShakeSensorEventListener shakeSensorEventListener;

    public ShakeManager(final SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    public void start(@NonNull final Listener listener) {
        this.listener = listener;

        stop();

        acceleration = 0.00f;
        accelerationCurrent = SensorManager.GRAVITY_EARTH;
        accelerationLast = SensorManager.GRAVITY_EARTH;

        shakeSensorEventListener = new ShakeSensorEventListener();
        sensorManager.registerListener(shakeSensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        if (shakeSensorEventListener != null) {
            sensorManager.unregisterListener(shakeSensorEventListener);
            shakeSensorEventListener = null;
        }
    }

    private class ShakeSensorEventListener implements SensorEventListener {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            accelerationLast = accelerationCurrent;
            accelerationCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = accelerationCurrent - accelerationLast;
            acceleration = acceleration * 0.9f + delta; // perform low-cut filter

            if (acceleration > 12) {
                if (listener != null) {
                    listener.onShake();
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}
