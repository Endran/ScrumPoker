/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.carddisplay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.cardselection.CardValue;

public class CardDisplayFragment extends Fragment {

    public static final String CARD_VALUE_KEY = "CARD_VALUE_KEY";
    public static final String COLOR_KEY = "COLOR_KEY";
    public static final String COLOR_DARK_KEY = "COLOR_DARK_KEY";

    @Bind(R.id.cardView)
    CardView cardView;

    @Bind(R.id.textViewNumber)
    TextView textViewNumber;

    @Bind(R.id.textViewName)
    TextView textViewName;

    public static CardDisplayFragment createFragment(final CardValue cardValue, final int color, final int colorDark) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(CARD_VALUE_KEY, cardValue);
        arguments.putInt(COLOR_KEY, color);
        arguments.putInt(COLOR_DARK_KEY, colorDark);

        CardDisplayFragment fragment = new CardDisplayFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.large_scrum_card, container, false);
        ButterKnife.bind(this, rootView);

        Bundle arguments = getArguments();

        CardValue cardValue = getCardValue(arguments);
        int color = getColor(arguments);
        int colorDark = getColorDark(arguments);

        textViewNumber.setText(cardValue.getStringId());

        textViewName.setText(cardValue.toString());
        textViewName.setBackgroundColor(colorDark);

        cardView.setCardBackgroundColor(color);

        return rootView;
    }

    private CardValue getCardValue(@NonNull final Bundle arguments) {
        return (CardValue) arguments.getSerializable(CARD_VALUE_KEY);
    }

    private int getColor(@NonNull final Bundle arguments) {
        return arguments.getInt(COLOR_KEY, 0);
    }

    private int getColorDark(@NonNull final Bundle arguments) {
        return arguments.getInt(COLOR_DARK_KEY, 0);
    }
}
