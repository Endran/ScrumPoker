/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.content.Context;
import android.content.SharedPreferences;

import nl.endran.scrumpoker.fragments.cardselection.DeckType;

public class Preferences {

    public static final String UNIQUE_ID_KEY= "UNIQUE_ID_KEY";
    public static final String DECK_TYPE_KEY = "DECK_TYPE_KEY";
    public static final String HIDE_AFTER_SELECTION_KEY = "HIDE_AFTER_SELECTION_KEY";
    public static final String SHOW_QUICK_SETTINGS_KEY = "SHOW_QUICK_SETTINGS_KEY";
    public static final String SHAKE_TO_REVEAL_KEY= "SHAKE_TO_REVEAL_KEY";
    public static final String NEARBY_ALLOWED_KEY= "NEARBY_ALLOWED_KEY";
    public static final String USE_NEARBY_KEY= "USE_NEARBY_KEY";

    private final SharedPreferences sharedPreferences;
    private Object uniqueId;

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

    public void setHideAfterSelection(final boolean shouldHide) {
        sharedPreferences.edit().putBoolean(HIDE_AFTER_SELECTION_KEY, shouldHide).apply();
    }

    public boolean shouldHideAfterSelection() {
        return sharedPreferences.getBoolean(HIDE_AFTER_SELECTION_KEY, true);
    }

    public void setShowQuickSettings(final boolean shouldShowQuickSettings) {
        sharedPreferences.edit().putBoolean(SHOW_QUICK_SETTINGS_KEY, shouldShowQuickSettings).apply();
    }

    public boolean shouldShowQuickSettings() {
        return sharedPreferences.getBoolean(SHOW_QUICK_SETTINGS_KEY, false);
    }

    public void setRevealAfterShake(final boolean shouldRevealAfterShake) {
        sharedPreferences.edit().putBoolean(SHAKE_TO_REVEAL_KEY, shouldRevealAfterShake).apply();
    }

    public boolean shouldRevealAfterShake() {
        return sharedPreferences.getBoolean(SHAKE_TO_REVEAL_KEY, true);
    }

    public boolean shouldUseNearby() {
        return sharedPreferences.getBoolean(USE_NEARBY_KEY, false);
    }

    public void setUseNearby(final boolean shouldUseNearby) {
        sharedPreferences.edit().putBoolean(USE_NEARBY_KEY, shouldUseNearby).apply();
    }

    public boolean isNearbyAllowed() {
        return sharedPreferences.getBoolean(NEARBY_ALLOWED_KEY, false);
    }

    public void setNearbyAllowed(final boolean nearbyAllowed) {
        sharedPreferences.edit().putBoolean(NEARBY_ALLOWED_KEY, nearbyAllowed).apply();
    }

    public String getUniqueId() {
        return sharedPreferences.getString(UNIQUE_ID_KEY, "");
    }

    public void setUniqueId(final String uniqueId) {
         sharedPreferences.edit().putString(UNIQUE_ID_KEY, uniqueId).apply();
    }
}
