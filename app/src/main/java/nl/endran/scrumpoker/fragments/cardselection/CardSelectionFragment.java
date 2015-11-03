/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.codetail.animation.SupportAnimator;
import nl.endran.scrumpoker.App;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.animation.AnimationManager;
import nl.endran.scrumpoker.animation.DummySupportAnimatorListener;
import nl.endran.scrumpoker.wrappers.Analytics;

public class CardSelectionFragment extends Fragment {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private AnimationManager animationManager = new AnimationManager();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_card_selection, container, false);
        ButterKnife.bind(this, rootView);

        CardSelectionAdapter adapter = new CardSelectionAdapter(new CardSelectionAdapter.Listener() {
            @Override
            public void onCardSelected(final View view, final CardSelection cardSelection) {
                Analytics analytics = ((App) (getContext().getApplicationContext())).getAnalytics();
                analytics.trackEvent("CardValue:" + getString(cardSelection.getCardValue().getStringId()));
                final SelectionBackgroundFragment fragment = SelectionBackgroundFragment.createFragment(cardSelection);

                SupportAnimator disappearAnimation = animationManager.getCircularDisappearAnimation(
                        recyclerView,
                        view.getX() + view.getMeasuredWidth() / 2,
                        view.getY() + view.getMeasuredHeight() / 2);

                disappearAnimation.addListener(new DummySupportAnimatorListener() {
                    @Override
                    public void onAnimationEnd() {
                        showFragment(fragment);
                    }
                });
                disappearAnimation.start();
            }
        });

        adapter.setCardValues(CardValue.values());

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void showFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String name = fragment.getClass().getName();
        transaction.addToBackStack(name);
        transaction.replace(R.id.contentFrame, fragment, name);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
