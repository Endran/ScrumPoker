/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.carddisplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.cardselection.CardValue;

public class CardDisplayActivity extends AppCompatActivity {

    public static final String CARD_VALUE_KEY = "CARD_VALUE_KEY";

    public static Intent createIntent(@NonNull final Context context, final CardValue cardValue) {
        Intent intent = new Intent(context, CardDisplayActivity.class);
        intent.putExtra(CARD_VALUE_KEY, cardValue);
        return intent;
    }

//    @Override
//    @CallSuper
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_display);

        CardValue cardValue = getCardValue(getIntent());

        TextView textView = ButterKnife.findById(this, R.id.textView);
        textView.setText(cardValue.getStringId());
    }

    private CardValue getCardValue(@NonNull final Intent intent) {
        return (CardValue) intent.getSerializableExtra(CARD_VALUE_KEY);
    }
}
