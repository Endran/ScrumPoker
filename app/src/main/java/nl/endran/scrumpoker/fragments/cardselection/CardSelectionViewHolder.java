/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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

    CardView cardView;

    @Bind(R.id.textViewNumber)
    TextView textView;

    @Bind(R.id.textViewName)
    TextView textViewName;

    @Nullable
    private Listener listener;

    public CardSelectionViewHolder(@NonNull final LayoutInflater layoutInflater, @NonNull final ViewGroup parent) {
        super(layoutInflater.inflate(LAYOUT_ID, parent, false));
        ButterKnife.bind(this, itemView);
        cardView = (CardView) itemView;
    }

    public void setListener(@Nullable final Listener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.clickView)
    public void onClickViewClicked() {
        if (listener != null) {
            listener.onCardClicked();
        }
    }
}
