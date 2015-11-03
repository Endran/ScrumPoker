/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.codetail.animation.SupportAnimator;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.animation.AnimationManager;
import nl.endran.scrumpoker.animation.DummySupportAnimatorListener;

public class CardDisplayFragment extends Fragment {

    public static final String CARD_VALUE_KEY = "CARD_VALUE_KEY";
    public static final String COLOR_KEY = "COLOR_KEY";
    public static final String COLOR_DARK_KEY = "COLOR_DARK_KEY";

    @Bind(R.id.cardView)
    CardView cardView;

    @Bind(R.id.textViewNumber)
    TextView textViewNumber;

    @Bind(R.id.textViewName)
    TextView textViewName;
    private AnimationManager animationManager = new AnimationManager();

    private View rootView;

    public static CardDisplayFragment createFragment(final CardValue cardValue, final int color, final int colorDark) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(CARD_VALUE_KEY, cardValue);
        arguments.putInt(COLOR_KEY, color);
        arguments.putInt(COLOR_DARK_KEY, colorDark);

        CardDisplayFragment fragment = new CardDisplayFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.large_scrum_card, container, false);
        ButterKnife.bind(this, rootView);

        Bundle arguments = getArguments();

        CardValue cardValue = getCardValue(arguments);
        int color = getColor(arguments);
        int colorDark = getColorDark(arguments);

        textViewNumber.setText(cardValue.getStringId());

        textViewName.setText(cardValue.toString());
        textViewName.setBackgroundColor(colorDark);

        cardView.setCardBackgroundColor(color);
        cardView.setVisibility(View.INVISIBLE);

        installBackPressedListener(new OnBackPressedListener() {
            @Override
            public boolean onBackPressed() {
                return disappearAndPopBackStack();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        rootView.setOnKeyListener(null);
        rootView = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                SupportAnimator revealAnimation = animationManager.getCircularRevealAnimation(
                        cardView,
                        cardView.getX() + cardView.getMeasuredWidth() / 2,
                        cardView.getY() + cardView.getMeasuredHeight() / 2);
                revealAnimation.start();
            }
        });
    }

    private boolean disappearAndPopBackStack() {
        SupportAnimator disappearAnimation = animationManager.getCircularDisappearAnimation(
                cardView,
                cardView.getX() + cardView.getMeasuredWidth() / 2,
                cardView.getY() + cardView.getMeasuredHeight() / 2);

        disappearAnimation.addListener(new DummySupportAnimatorListener() {
            @Override
            public void onAnimationEnd() {
                getFragmentManager().popBackStack();
            }
        });
        disappearAnimation.start();

        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        cardView.setVisibility(View.INVISIBLE);
    }

    private CardValue getCardValue(@NonNull final Bundle arguments) {
        return (CardValue) arguments.getSerializable(CARD_VALUE_KEY);
    }

    private int getColor(@NonNull final Bundle arguments) {
        return arguments.getInt(COLOR_KEY, 0);
    }

    private int getColorDark(@NonNull final Bundle arguments) {
        return arguments.getInt(COLOR_DARK_KEY, 0);
    }

    private void installBackPressedListener(@NonNull final OnBackPressedListener onBackPressedListener) {
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    return onBackPressedListener.onBackPressed();
                }

                return false;
            }
        });
    }

    public interface OnBackPressedListener {
        boolean onBackPressed();
    }
}
