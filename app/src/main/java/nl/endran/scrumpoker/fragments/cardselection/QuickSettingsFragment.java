/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crashlytics.android.answers.CustomEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import nl.endran.scrumpoker.App;
import nl.endran.scrumpoker.Preferences;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.util.ShakeManager;
import nl.endran.scrumpoker.wrappers.Tracking;

public class QuickSettingsFragment extends Fragment {

    public interface Listener {
        void onShowCardClicked();

        void onNearbyPermissionRequested();

        void onStopNearby();
    }

    @Bind(R.id.linearLayoutQuickSettings)
    View viewQuickSettings;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.switchHideAfterSelection)
    SwitchCompat switchHideAfterSelection;

    @Bind(R.id.switchShowQuickSettings)
    SwitchCompat switchShowQuickSettings;

    @Bind(R.id.switchShakeToReveal)
    SwitchCompat switchShakeToReveal;

    @Bind(R.id.switchUseNearby)
    SwitchCompat switchUseNearby;

    @Nullable
    private Listener listener;
    private Preferences preferences;
    private ShakeManager shakeManager;
    private Tracking tracking;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selection_background, container, false);
        ButterKnife.bind(this, rootView);

        App app = (App) rootView.getContext().getApplicationContext();
        tracking = app.getTracking();

        fab.setVisibility(View.INVISIBLE);

        shakeManager = new ShakeManager((SensorManager) rootView.getContext().getSystemService(Context.SENSOR_SERVICE));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        tracking = null;
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        tracking.logCustom(new CustomEvent("Reveal")
                .putCustomAttribute("Type", "Click"));
        informListener();
    }

    private void informListener() {
        if (listener != null) {
            listener.onShowCardClicked();
        }
    }

    public void show(@NonNull final Listener listener) {
        this.listener = listener;
        fab.show();

        boolean shouldHideAfterSelection = preferences.shouldHideAfterSelection();
        boolean shouldShowQuickSettings = preferences.shouldShowQuickSettings();
        boolean shouldRevealAfterShake = preferences.shouldRevealAfterShake();
        boolean shouldUseNearby = preferences.shouldUseNearby();

        switchHideAfterSelection.setChecked(shouldHideAfterSelection);
        switchShowQuickSettings.setChecked(shouldShowQuickSettings && shouldHideAfterSelection);
        switchShakeToReveal.setChecked(shouldRevealAfterShake);
        switchUseNearby.setChecked(shouldUseNearby);

        viewQuickSettings.setVisibility(shouldShowQuickSettings ? View.VISIBLE : View.INVISIBLE);

        if (!shouldHideAfterSelection) {
            tracking.logCustom(new CustomEvent("Reveal")
                    .putCustomAttribute("Type", "Instant"));
            informListener();
        } else if (shouldRevealAfterShake) {
            installShakeListener();
        }
    }

    private void installShakeListener() {
        shakeManager.start(new ShakeManager.Listener() {
            @Override
            public void onShake() {
                Context context = getContext();
                if (context != null && tracking != null) {
                    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    tracking.logCustom(new CustomEvent("Reveal")
                            .putCustomAttribute("Type", "Shake"));
                    informListener();
                }
            }
        });
    }

    public void hide() {
        shakeManager.stop();

        listener = null;
        fab.hide();
    }

    @OnCheckedChanged(R.id.switchHideAfterSelection)
    public void onSwitchHideAfterSelectionChanged(final boolean checked) {
        preferences.setHideAfterSelection(checked);

        if (!checked) {
            switchShowQuickSettings.setChecked(false);
            switchShakeToReveal.setChecked(false);
            switchUseNearby.setChecked(false);
        }
    }

    @OnCheckedChanged(R.id.switchShowQuickSettings)
    public void onSwitchShowQuickSettingsChanged(final boolean checked) {
        boolean oldChecked = preferences.shouldShowQuickSettings();
        preferences.setShowQuickSettings(checked);
        if (!checked && oldChecked) {
            Toast.makeText(getContext(), R.string.quick_settings_hidden, Toast.LENGTH_SHORT).show();
        }
        if (checked) {
            switchHideAfterSelection.setChecked(true);
        }
    }

    @OnCheckedChanged(R.id.switchShakeToReveal)
    public void onSwitchShakeToRevealChanged(final boolean checked) {
        preferences.setRevealAfterShake(checked);
        if (checked) {
            switchHideAfterSelection.setChecked(true);
            installShakeListener();
        }
    }

    @OnCheckedChanged(R.id.switchUseNearby)
    public void onSwitchUseNearbySelectionChanged(final boolean checked) {
        if (!checked) {
            preferences.setUseNearby(false);
            if (listener != null) {
                listener.onStopNearby();
            }
        } else {
            if (preferences.isNearbyAllowed()) {
                preferences.setUseNearby(true);
                switchHideAfterSelection.setChecked(true);
            }
            if (listener != null) {
                listener.onNearbyPermissionRequested();
            }
        }
    }

    public void setPreferences(final Preferences preferences) {
        this.preferences = preferences;
    }
}
