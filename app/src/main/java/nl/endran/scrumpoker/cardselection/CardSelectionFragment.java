/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.cardselection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.endran.scrumpoker.App;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.carddisplay.CardDisplayActivity;

public class CardSelectionFragment extends Fragment {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_card_selection, container, false);
        ButterKnife.bind(this, rootView);

        CardSelectionAdapter adapter = new CardSelectionAdapter(new CardSelectionAdapter.Listener() {
            @Override
            public void onCardSelected(final View view, final CardValue cardValue) {
                Tracker tracker = ((App) (getContext().getApplicationContext())).getDefaultTracker();
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("CardValue:" + getString(cardValue.getStringId()))
                        .build());

                getContext().startActivity(CardDisplayActivity.createIntent(getContext(), cardValue));
            }
        });
        adapter.setCardValues(CardValue.values());

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
