/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.animation;

import android.support.annotation.NonNull;
import android.view.View;

public class SetInvisibleOnSupportAnimatorEndListener extends DummySupportAnimatorListener {

    private View view;

    public SetInvisibleOnSupportAnimatorEndListener(@NonNull final View view) {
        this.view = view;
    }

    @Override
    public void onAnimationEnd() {
        view.setVisibility(View.INVISIBLE);
        view = null;
    }
}
