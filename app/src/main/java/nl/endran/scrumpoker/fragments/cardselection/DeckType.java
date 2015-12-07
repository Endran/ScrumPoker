/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, CardValue.Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

public enum DeckType {
    STANDARD(
            CardValue.ZERO, CardValue.HALF, CardValue.ONE, CardValue.TWO, CardValue.THREE, CardValue.FIVE, CardValue.EIGHT, CardValue.THIRTEEN, CardValue.TWENTY, CardValue.FORTY, CardValue.HUNDRED, CardValue.INFINITE, CardValue.UNKNOWN, CardValue.COFFEE
    ),
    FIBONACCI(
            CardValue.ZERO, CardValue.ONE, CardValue.TWO, CardValue.THREE, CardValue.FIVE, CardValue.EIGHT, CardValue.THIRTEEN, CardValue.TWENTY_ONE, CardValue.THIRTY_FOUR, CardValue.FIFTY_FIVE, CardValue.EIGHTY_NINE, CardValue.INFINITE, CardValue.UNKNOWN, CardValue.COFFEE
    ),
    SHIRT(
            CardValue.XXS, CardValue.XS, CardValue.S, CardValue.M, CardValue.L, CardValue.XL, CardValue.XXL, CardValue.INFINITE, CardValue.UNKNOWN, CardValue.COFFEE
    ),
    NATURAL(
            CardValue.ZERO, CardValue.ONE, CardValue.TWO, CardValue.THREE, CardValue.FOUR, CardValue.FIVE, CardValue.SIX, CardValue.SEVEN, CardValue.EIGHT, CardValue.NINE,
            CardValue.TEN, CardValue.ELEVEN, CardValue.TWELVE, CardValue.THIRTEEN, CardValue.FOURTEEN, CardValue.FIFTEEN, CardValue.SIXTEEN, CardValue.SEVENTEEN, CardValue.EIGHTEEN, CardValue.NINETEEN,
            CardValue.TWENTY, CardValue.INFINITE, CardValue.UNKNOWN, CardValue.COFFEE
    );

    private CardValue[] values;

    DeckType(final CardValue... values) {
        this.values = values;
    }

    public CardValue[] getValues() {
        return values;
    }
}
