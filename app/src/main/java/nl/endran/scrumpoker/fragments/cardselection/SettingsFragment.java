/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import nl.endran.scrumpoker.Preferences;
import nl.endran.scrumpoker.R;

public class SettingsFragment extends Fragment {

    public interface Listener {
        void onNearbyPermissionRequested();
    }

    @Bind(R.id.switchHideAfterSelection)
    SwitchCompat switchHideAfterSelection;

    @Bind(R.id.switchShowQuickSettings)
    SwitchCompat switchShowQuickSettings;

    @Bind(R.id.switchShakeToReveal)
    SwitchCompat switchShakeToReveal;

    @Bind(R.id.switchUseNearby)
    SwitchCompat switchUseNearby;

    private Preferences preferences;

    @Nullable
    private Listener listener;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, rootView);

        preferences = new Preferences(rootView.getContext());

        switchHideAfterSelection.setChecked(preferences.shouldHideAfterSelection());
        switchShowQuickSettings.setChecked(preferences.shouldShowQuickSettings());
        switchShakeToReveal.setChecked(preferences.shouldRevealAfterShake());
        switchUseNearby.setChecked(preferences.shouldUseNearby());

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listener = null;
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
        preferences.setShowQuickSettings(checked);
        if (checked) {
            switchHideAfterSelection.setChecked(true);
        }
    }

    @OnCheckedChanged(R.id.switchShakeToReveal)
    public void onSwitchShakeToRevealChanged(final boolean checked) {
        preferences.setRevealAfterShake(checked);
        if (checked) {
            switchHideAfterSelection.setChecked(true);
        }
    }

    @OnCheckedChanged(R.id.switchUseNearby)
    public void onSwitchUseNearbySelectionChanged(final boolean checked) {
        if (!checked) {
            preferences.setUseNearby(false);
        } else if (preferences.isNearbyAllowed()) {
            preferences.setUseNearby(true);
            switchHideAfterSelection.setChecked(true);
        } else if (listener != null) {
            switchHideAfterSelection.setChecked(true);
            listener.onNearbyPermissionRequested();
        }
    }

    public void setListener(@Nullable final Listener listener) {
        this.listener = listener;
    }
}
