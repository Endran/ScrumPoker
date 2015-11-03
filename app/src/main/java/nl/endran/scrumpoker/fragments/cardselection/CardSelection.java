/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class CardSelection implements Serializable {

    @NonNull
    private CardValue cardValue;

    @ColorInt
    private int color;

    @ColorInt
    private int colorDark;

    public CardSelection(@NonNull final CardValue cardValue, final int color, final int colorDark) {
        this.cardValue = cardValue;
        this.color = color;
        this.colorDark = colorDark;
    }

    @NonNull
    public CardValue getCardValue() {
        return cardValue;
    }

    public int getColor() {
        return color;
    }

    public int getColorDark() {
        return colorDark;
    }
}
