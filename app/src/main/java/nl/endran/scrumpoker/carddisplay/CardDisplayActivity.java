/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.carddisplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import butterknife.ButterKnife;
import nl.endran.scrumpoker.BaseActivity;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.cardselection.CardValue;

public class CardDisplayActivity extends BaseActivity {

    public static final String CARD_VALUE_KEY = "CARD_VALUE_KEY";
    public static final String COLOR_KEY = "COLOR_KEY";

    public static Intent createIntent(@NonNull final Context context, final CardValue cardValue, final int color) {
        Intent intent = new Intent(context, CardDisplayActivity.class);
        intent.putExtra(CARD_VALUE_KEY, cardValue);
        intent.putExtra(COLOR_KEY, color);
        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CardValue cardValue = getCardValue(getIntent());

        TextView textView = ButterKnife.findById(this, R.id.textView);
        textView.setText(cardValue.getStringId());

        CardView cardView = ButterKnife.findById(this, R.id.cardView);
        cardView.setCardBackgroundColor(getColor(getIntent()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_display;
    }

    @Override
    protected String getPageName() {
        return "CardDisplayActivity";
    }

    private CardValue getCardValue(@NonNull final Intent intent) {
        return (CardValue) intent.getSerializableExtra(CARD_VALUE_KEY);
    }

    private int getColor(@NonNull final Intent intent) {
        return intent.getIntExtra(COLOR_KEY, 0);
    }
}
