/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.cardselection;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.endran.scrumpoker.App;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.TransitionHelper;
import nl.endran.scrumpoker.carddisplay.CardDisplayActivity;
import nl.endran.scrumpoker.wrappers.Analytics;

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
            public void onCardSelected(final View view, final CardValue cardValue, final int color, final int colorDark) {
                Analytics analytics = ((App) (getContext().getApplicationContext())).getAnalytics();
                analytics.trackEvent("CardValue:" + getString(cardValue.getStringId()));

                Intent intent = CardDisplayActivity.createIntent(getContext(), cardValue, color, colorDark);

                FragmentActivity activity = getActivity();

                Bundle transitionBundle = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                            new Pair<>(view, activity.getString(R.string.transition_card)));
                    ActivityOptionsCompat sceneTransitionAnimation = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(activity, pairs);
                    transitionBundle = sceneTransitionAnimation.toBundle();
                }

                ActivityCompat.startActivity(getActivity(), intent, transitionBundle);
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
