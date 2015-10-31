/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.cardselection;

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
    FORTY(R.string.forty),
    HUNDRED(R.string.hundred),
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
