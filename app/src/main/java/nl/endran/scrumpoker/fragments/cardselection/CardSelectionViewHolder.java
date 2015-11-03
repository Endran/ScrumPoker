/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.endran.scrumpoker.R;

public class CardSelectionViewHolder extends RecyclerView.ViewHolder {

    public interface Listener {
        void onCardClicked();
    }

    public static final int LAYOUT_ID = R.layout.scrum_card;

    @Bind(R.id.textViewNumber)
    TextView textView;

    @Bind(R.id.textViewName)
    TextView textViewName;

    @Nullable
    private Listener listener;
    private BounceUpAndDownAnimator animator;

    public CardSelectionViewHolder(@NonNull final LayoutInflater layoutInflater, @NonNull final ViewGroup parent, @NonNull final BounceUpAndDownAnimator animator) {
        super(layoutInflater.inflate(LAYOUT_ID, parent, false));
        this.animator = animator;
        ButterKnife.bind(this, itemView);
    }

    public void setListener(@Nullable final Listener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.clickView)
    public void onClickViewClicked() {
        animator.animate(itemView, new BounceUpAndDownAnimator.Listener() {
            @Override
            public void onFinished() {
                informListener();
            }
        });
    }

    private void informListener() {
        if (listener != null) {
            listener.onCardClicked();
        }
    }
}
