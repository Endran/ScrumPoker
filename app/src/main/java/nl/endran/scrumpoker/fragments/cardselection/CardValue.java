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
    FOUR(R.string.four),
    FIVE(R.string.five),
    SIX(R.string.six),
    SEVEN(R.string.seven),
    EIGHT(R.string.eigth),
    NINE(R.string.nine),
    TEN(R.string.ten),
    ELEVEN(R.string.eleven),
    TWELVE(R.string.twelve),
    THIRTEEN(R.string.thirteen),
    FOURTEEN(R.string.fourteen),
    FIFTEEN(R.string.fifteen),
    SIXTEEN(R.string.sixteen),
    SEVENTEEN(R.string.seventeen),
    EIGHTEEN(R.string.eighteen),
    NINETEEN(R.string.nineteen),
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
}