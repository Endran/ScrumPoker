/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.codetail.animation.SupportAnimator;
import nl.endran.scrumpoker.App;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.animation.AnimationManager;
import nl.endran.scrumpoker.animation.SetVisibleOnAnimationStartListener;
import nl.endran.scrumpoker.wrappers.Analytics;

public class CardSelectionFragment extends Fragment {

    public interface Listener {
        void onCardSelected(CardSelection cardSelection, final Point vanishingPoint);
    }

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private AnimationManager animationManager = new AnimationManager();
    private CardSelectionAdapter adapter;
    private Listener listener;

    private boolean showing;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_card_selection, container, false);
        ButterKnife.bind(this, rootView);

        adapter = new CardSelectionAdapter(new CardSelectionAdapter.Listener() {
            @Override
            public void onCardSelected(final View view, final CardSelection cardSelection) {
                Analytics analytics = ((App) (getContext().getApplicationContext())).getAnalytics();
                analytics.trackEvent("CardValue:" + getString(cardSelection.getCardValue().getStringId()));

                int centerX = (int) (view.getX() + view.getMeasuredWidth() / 2);
                int centerY = (int) (view.getY() + view.getMeasuredHeight() / 2);
                Point vanishingPoint = new Point(centerX, centerY);

                if (listener != null) {
                    listener.onCardSelected(cardSelection, vanishingPoint);
                }
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void show(CardValue[] cardValues, Listener listener) {
        if (!showing) {
            showing = true;
            this.listener = listener;
            adapter.setCardValues(cardValues);

            Animation animation = animationManager.createAnimation(getContext(), R.anim.fade_in);
            animation.setAnimationListener(new SetVisibleOnAnimationStartListener(recyclerView));
            recyclerView.startAnimation(animation);
        }
    }

    public void hide(final Point vanishingPoint) {
        if (showing) {
            showing = false;
            SupportAnimator disappearAnimation = animationManager.getCircularDisappearAnimation(
                    recyclerView,
                    vanishingPoint.x,
                    vanishingPoint.y);
            disappearAnimation.start();
        }
    }

    public boolean isShowing() {
        return showing;
    }
}
