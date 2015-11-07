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

import butterknife.Bind;
import butterknife.ButterKnife;
import io.codetail.animation.SupportAnimator;
import nl.endran.scrumpoker.App;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.animation.AnimationManager;
import nl.endran.scrumpoker.wrappers.Analytics;

public class CardSelectionFragment extends Fragment {

    public interface Listener {
        void onCardSelected(CardSelection cardSelection);
    }

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private AnimationManager animationManager = new AnimationManager();
    private CardSelectionAdapter adapter;
    private Listener listener;

    private boolean showing;

    @Nullable
    private Point vanishingPoint;

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
                vanishingPoint = new Point(centerX, centerY);

                if (listener != null) {
                    listener.onCardSelected(cardSelection);
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

    public void show(Listener listener) {
        if (!showing) {
            showing = true;
            this.listener = listener;

            recyclerView.setVisibility(View.VISIBLE);

            if (vanishingPoint != null) {
                SupportAnimator animation = animationManager.getCircularRevealAnimation(
                        recyclerView,
                        vanishingPoint.x,
                        vanishingPoint.y);
                animation.start();
                this.vanishingPoint = null;
            }
        }
    }

    public void setCardValues(final CardValue[] cardValues) {
        adapter.setCardValues(cardValues);
    }

    public void hide() {
        if (showing) {
            showing = false;

            Point vanishingPoint = getVanishingPoint();
            SupportAnimator animation = animationManager.getCircularDisappearAnimation(
                    recyclerView,
                    vanishingPoint.x,
                    vanishingPoint.y);
            animation.start();
        }
    }

    private Point getVanishingPoint() {
        if (vanishingPoint != null) {
            return vanishingPoint;
        } else {
            int centerX = (int) (recyclerView.getX() + recyclerView.getMeasuredWidth() / 2);
            int centerY = (int) (recyclerView.getY() + recyclerView.getMeasuredHeight() / 2);
            return new Point(centerX, centerY);
        }
    }

    public boolean isShowing() {
        return showing;
    }
}
