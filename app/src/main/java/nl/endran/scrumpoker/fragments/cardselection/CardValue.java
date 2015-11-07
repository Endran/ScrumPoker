/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import nl.endran.scrumpoker.R;

public enum CardValue {
    ZERO(R.string.zero),
    HALF(R.string.half),
    ONE(R.string.one),
    TWO(R.string.two),
    THREE(R.string.three),
    FIVE(R.string.five),
    EIGHT(R.string.eigth),
    THIRTEEN(R.string.thirteen),
    TWENTY(R.string.twenty),
    TWENTY_ONE(R.string.twenty_one),
    THIRTY_FOUR(R.string.thirty_four),
    FORTY(R.string.forty),
    FIFTY_FIVE(R.string.fifty_five),
    EIGHTY_NINE(R.string.eighty_nine),
    HUNDRED(R.string.hundred),

    XXS(R.string.xxs),
    XS(R.string.xs),
    S(R.string.s),
    M(R.string.m),
    L(R.string.l),
    XL(R.string.xl),
    XXL(R.string.xxl),

    INFINITE(R.string.infinite),
    UNKNOWN(R.string.unknown),
    COFFEE(R.string.coffee);

    private int stringId;

    CardValue(final int stringId) {
        this.stringId = stringId;
    }

    public int getStringId() {
        return stringId;
    }

    public static CardValue[] getStandard() {
        return new CardValue[]{
                ZERO, HALF, ONE, TWO, THREE, FIVE, EIGHT, THIRTEEN, TWENTY, FORTY, HUNDRED, INFINITE, UNKNOWN, COFFEE};
    }

    public static CardValue[] getFibonacci() {
        return new CardValue[]{
                ZERO, ONE, TWO, THREE, FIVE, EIGHT, THIRTEEN, TWENTY_ONE, THIRTY_FOUR, FIFTY_FIVE, EIGHTY_NINE, INFINITE, UNKNOWN, COFFEE};
    }

    public static CardValue[] getShirt() {
        return new CardValue[]{
                XXS, XS, S, M, L, XL, XXL, INFINITE, UNKNOWN, COFFEE};
    }
}