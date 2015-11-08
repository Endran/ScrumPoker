/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.os.Bundle;
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

    @Bind(R.id.switchHideAfterSelection)
    SwitchCompat switchHideAfterSelection;

    @Bind(R.id.switchShowQuickSettings)
    SwitchCompat switchShowQuickSettings;

    private Preferences preferences;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, rootView);

        preferences = new Preferences(rootView.getContext());

        switchHideAfterSelection.setChecked(preferences.shouldHideAfterSelection());
        switchShowQuickSettings.setChecked(preferences.shouldShowQuickSettings());

        return rootView;
    }

    @OnCheckedChanged(R.id.switchHideAfterSelection)
    public void onSwitchHideAfterSelectionChanged(final boolean checked) {
        preferences.setHideAfterSelection(checked);
        if (!checked) {
            switchShowQuickSettings.setChecked(false);
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
    }

    @OnCheckedChanged(R.id.switchUseNearby)
    public void onSwitchUseNearbySelectionChanged(final boolean checked) {
    }
}
