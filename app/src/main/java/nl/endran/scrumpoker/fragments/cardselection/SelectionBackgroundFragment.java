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

import butterknife.Bind;
import butterknife.ButterKnife;
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
}
