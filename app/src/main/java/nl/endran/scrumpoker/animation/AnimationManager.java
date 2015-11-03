/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.animation;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class AnimationManager {

    public AnimationManager() {
    }

    @NonNull
    public SupportAnimator getCircularDisappearAnimation(@NonNull final View view, final float centerX, final float centerY) {

        int radius = getRadius(view.getWidth(), view.getHeight());
        SupportAnimator animator = createCircularSupportAnimator(view, (int) centerX, (int) centerY, radius, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new SetInvisibleOnSupportAnimatorEndListener(view));

        return animator;
    }

    @NonNull
    protected SupportAnimator createCircularSupportAnimator(final @NonNull View view,
                                                            final int centerX, final int centerY,
                                                            final int startRadius, final int endRadius) {
        return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
    }

    @NonNull
    public SupportAnimator getCircularRevealAnimation(@NonNull final View view, final float centerX, final float centerY) {

        int radius = getRadius(view.getWidth(), view.getHeight());
        SupportAnimator animator = createCircularSupportAnimator(view, (int) centerX, (int) centerY, 0, radius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new SetVisibleOnSupportAnimatorStartListener(view));

        return animator;
    }

    private int getRadius(final int width, final int height) {
        return (int) Math.sqrt(width * width + height * height);
    }

    public Animation createAnimation(final @NonNull Context context, final @AnimRes int animId) {
        return AnimationUtils.loadAnimation(context, animId);
    }
}