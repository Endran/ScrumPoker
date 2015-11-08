/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import nl.endran.scrumpoker.Preferences;
import nl.endran.scrumpoker.R;

public class SelectionBackgroundFragment extends Fragment {

    public interface Listener {
        void onShowCardClicked();
    }

    @Bind(R.id.linearLayoutQuickSettings)
    View viewQuickSettings;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.switchHideAfterSelection)
    SwitchCompat switchHideAfterSelection;

    @Bind(R.id.switchShowQuickSettings)
    SwitchCompat switchShowQuickSettings;

    @Nullable
    private Listener listener;
    private Preferences preferences;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selection_background, container, false);
        ButterKnife.bind(this, rootView);
        fab.setVisibility(View.INVISIBLE);
        return rootView;
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
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
        switchHideAfterSelection.setChecked(shouldHideAfterSelection);
        if (!shouldHideAfterSelection) {
            informListener();
        }

        boolean shouldShowQuickSettings = preferences.shouldShowQuickSettings();
        viewQuickSettings.setVisibility(shouldShowQuickSettings ? View.VISIBLE : View.INVISIBLE);
        switchShowQuickSettings.setChecked(shouldHideAfterSelection && shouldShowQuickSettings);
    }

    public void hide() {
        listener = null;
        fab.hide();
    }

    @OnCheckedChanged(R.id.switchHideAfterSelection)
    public void onSwitchHideAfterSelectionChanged(final boolean checked) {
        preferences.setHideAfterSelection(checked);

        if(!checked) {
            switchShowQuickSettings.setChecked(checked);
        }
    }

    @OnCheckedChanged(R.id.switchShowQuickSettings)
    public void onSwitchShowQuickSettingsChanged(final boolean checked) {
        boolean oldChecked = preferences.shouldShowQuickSettings();
        preferences.setShowQuickSettings(checked);
        if(!checked && oldChecked) {
            Toast.makeText(getContext(), R.string.quick_settings_hidden, Toast.LENGTH_SHORT).show();
        }
    }

    @OnCheckedChanged(R.id.switchShakeToReveal)
    public void onSwitchShakeToRevealChanged(final boolean checked) {
        Toast.makeText(getContext(), R.string.quick_settings_not_implemented, Toast.LENGTH_SHORT).show();
    }

    @OnCheckedChanged(R.id.switchUseNearby)
    public void onSwitchUseNearbySelectionChanged(final boolean checked) {
        Toast.makeText(getContext(), R.string.quick_settings_not_implemented, Toast.LENGTH_SHORT).show();
    }

    public void setPreferences(final Preferences preferences) {
        this.preferences = preferences;
    }
}
