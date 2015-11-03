/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.cardselection;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CardSelectionAdapter extends RecyclerView.Adapter<CardSelectionViewHolder> {

    public interface Listener {
        void onCardSelected(View view, CardValue cardValue, final int color, final int colorDark);
    }

    @NonNull
    private CardValue[] cardValues = new CardValue[]{};

    @NonNull
    private final Listener listener;

    public CardSelectionAdapter(@NonNull final Listener listener) {
        this.listener = listener;
    }

    @Override
    public CardSelectionViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new CardSelectionViewHolder(layoutInflater, parent, new BounceUpAndDownAnimator());
    }

    @Override
    public void onBindViewHolder(final CardSelectionViewHolder holder, final int position) {
        final CardValue cardValue = cardValues[position];

        float h = 90 - 90 * ((float) position) / cardValues.length;
        final int color = Color.HSVToColor(new float[]{h, .5f, 1f});
        final int colorDark = Color.HSVToColor(new float[]{h, .9f, 1f});

//        int color = ContextCompat.getColor(holder.itemView.getContext(), cardValue.getColorId());
        ((CardView) holder.itemView).setCardBackgroundColor(color);

        holder.textViewName.setText(cardValue.toString());
        holder.textViewName.setBackgroundColor(colorDark);

        holder.textView.setText(cardValue.getStringId());
        holder.setListener(new CardSelectionViewHolder.Listener() {
            @Override
            public void onCardClicked() {
                listener.onCardSelected(holder.itemView, cardValue, color, colorDark);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardValues.length;
    }

    public void setCardValues(@NonNull final CardValue[] cardValues) {
        this.cardValues = cardValues;
        notifyDataSetChanged();
    }
}
