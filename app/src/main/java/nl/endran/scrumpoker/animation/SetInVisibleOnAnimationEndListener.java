/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.animation;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;

/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */
public class SetInVisibleOnAnimationEndListener extends DummyAnimationListener {

    private View view;

    public SetInVisibleOnAnimationEndListener(@NonNull final View view) {
        this.view = view;
    }

    @Override
    public void onAnimationStart(final Animation animation) {
    }

    @Override
    public void onAnimationEnd(final Animation animation) {
        animation.setAnimationListener(null);
        view.setVisibility(View.INVISIBLE);
        view = null;
    }
}
