/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.endran.scrumpoker.R;

public class SelectionBackgroundFragment extends Fragment {

    public static final String CARD_SELECTION_KEY = "CARD_SELECTION_KEY";

    public static SelectionBackgroundFragment createFragment(final CardSelection cardSelection) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(CARD_SELECTION_KEY, cardSelection);

        SelectionBackgroundFragment fragment = new SelectionBackgroundFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selection_background, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.frameLayoutQuickSettings)
    public void onFrameLayoutQuickSettingsClicked() {
        final CardDisplayFragment fragment = CardDisplayFragment.createFragment(getCardSelection(getArguments()));

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String name = fragment.getClass().getName();
        transaction.replace(R.id.contentFrame, fragment, name);
        transaction.commit();
    }

    private CardSelection getCardSelection(@NonNull final Bundle arguments) {
        return (CardSelection) arguments.getSerializable(CARD_SELECTION_KEY);
    }
}
