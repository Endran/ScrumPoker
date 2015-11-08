/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.content.Context;
import android.content.SharedPreferences;

import nl.endran.scrumpoker.fragments.cardselection.DeckType;

public class Preferences {

    public static final String DECK_TYPE_KEY = "DECK_TYPE_KEY";

    private final SharedPreferences sharedPreferences;

    public Preferences(final Context context) {
        this.sharedPreferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    public void setDeckType(final DeckType deckType) {
        sharedPreferences.edit().putInt(DECK_TYPE_KEY, deckType.ordinal()).apply();
    }

    public DeckType getDeckType() {
        int deckTypeOrdinal = sharedPreferences.getInt(DECK_TYPE_KEY, 0);
        return DeckType.values()[deckTypeOrdinal];
    }
}
