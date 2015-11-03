/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import nl.endran.scrumpoker.animation.DummyAnimatorListener;

public class BounceUpAndDownAnimator {

    public interface Listener {
        void onFinished();
    }

    public void animate(@NonNull final View view, @NonNull final Listener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            listener.onFinished();
        } else {
            animateLollipop(view, listener);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateLollipop(final @NonNull View view, final @NonNull Listener listener) {
        float elevation = view.getElevation();
        view.animate()
                .translationZ(elevation * 2)
                .setDuration(200)
                .setListener(new DummyAnimatorListener() {
                    @Override
                    public void onAnimationEnd(final Animator animator) {
                        view.animate()
                                .translationZ(0)
                                .setDuration(200)
                                .setListener(new DummyAnimatorListener() {
                                    @Override
                                    public void onAnimationEnd(final Animator animator) {
                                        view.animate().setListener(null);
                                        listener.onFinished();
                                    }
                                }).start();
                    }
                }).start();
    }
}
