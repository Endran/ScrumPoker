/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.animation;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;

public class SetVisibleOnAnimationStartListener extends DummyAnimationListener {

    private View view;

    public SetVisibleOnAnimationStartListener(@NonNull final View view) {
        this.view = view;
    }

    @Override
    public void onAnimationStart(final Animation animation) {
        animation.setAnimationListener(null);
        view.setVisibility(View.VISIBLE);
        view = null;
    }
}

