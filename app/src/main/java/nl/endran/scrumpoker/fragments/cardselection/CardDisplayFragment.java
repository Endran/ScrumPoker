/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.endran.scrumpoker.R;
import nl.endran.scrumpoker.animation.AnimationManager;
import nl.endran.scrumpoker.animation.SetInVisibleOnAnimationEndListener;
import nl.endran.scrumpoker.animation.SetVisibleOnAnimationStartListener;

public class CardDisplayFragment extends Fragment {

    @Bind(R.id.cardView)
    CardView cardView;

    @Bind(R.id.textViewNumber)
    TextView textViewNumber;

    @Bind(R.id.textViewName)
    TextView textViewName;

    private AnimationManager animationManager = new AnimationManager();
    private boolean showing;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_display, container, false);
        ButterKnife.bind(this, rootView);
        cardView.setVisibility(View.INVISIBLE);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void show(CardSelection cardSelection) {
        if (!showing) {
            showing = true;

            int stringId = cardSelection.getCardValue().getStringId();
            if (stringId == R.string.coffee) {
                textViewNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, textViewName.getResources().getDimension(R.dimen.display_mid_text_size));
            } else {
                textViewNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, textViewName.getResources().getDimension(R.dimen.display_large_text_size));
            }

            textViewNumber.setText(stringId);
            textViewName.setText(cardSelection.getCardValue().toString().replace("_", " "));
            textViewName.setBackgroundColor(cardSelection.getColorDark());
            cardView.setCardBackgroundColor(cardSelection.getColor());

            Animation animation = animationManager.createAnimation(getContext(), R.anim.slide_in_bottom_center_with_bounce);
            animation.setAnimationListener(new SetVisibleOnAnimationStartListener(cardView));
            cardView.startAnimation(animation);
        }
    }

    public void hide() {
        if (showing) {
            showing = false;
            Animation animation = animationManager.createAnimation(getContext(), R.anim.slide_out_bottom_center);
            animation.setAnimationListener(new SetInVisibleOnAnimationEndListener(cardView));
            cardView.startAnimation(animation);
        }
    }
}
