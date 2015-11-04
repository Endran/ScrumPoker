/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import nl.endran.scrumpoker.R;

public class SelectionBackgroundFragment extends Fragment {

    public interface Listener {
        void onShowCardClicked();
    }

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Nullable
    private Listener listener;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selection_background, container, false);
        ButterKnife.bind(this, rootView);
        fab.setVisibility(View.INVISIBLE);
        return rootView;
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        if (listener != null) {
            listener.onShowCardClicked();
        }
    }

    public void show(@NonNull final Listener listener) {
        this.listener = listener;
        fab.show();
    }

    public void hide() {
        listener = null;
        fab.hide();
    }

    @OnCheckedChanged(R.id.switchHideAfterSelection)
    public void onSwitchHideAfterSelectionChanged(final boolean checked) {
        Toast.makeText(getContext(), R.string.quick_settings_not_implemented, Toast.LENGTH_SHORT).show();
    }

    @OnCheckedChanged(R.id.switchShowQuickSettings)
    public void onSwitchShowQuickSettingsChanged(final boolean checked) {
        Toast.makeText(getContext(), R.string.quick_settings_not_implemented, Toast.LENGTH_SHORT).show();
    }

    @OnCheckedChanged(R.id.switchShakeToReveal)
    public void onSwitchShakeToRevealChanged(final boolean checked) {
        Toast.makeText(getContext(), R.string.quick_settings_not_implemented, Toast.LENGTH_SHORT).show();
    }

    @OnCheckedChanged(R.id.switchUseNearby)
    public void onSwitchUseNearbySelectionChanged(final boolean checked) {
        Toast.makeText(getContext(), R.string.quick_settings_not_implemented, Toast.LENGTH_SHORT).show();
    }
}
