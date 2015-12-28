package com.herkiusdev.dicemaster.util;

import android.support.v4.view.ViewPager;
import android.view.View;

public class FadePageTransformer implements ViewPager.PageTransformer {
    public void transformPage(View view, float position) {

        final int pageWidth = view.getWidth();

        if (position < -0.999f) {
            view.setAlpha(0);
            view.setVisibility(View.GONE);
            view.setTranslationX(pageWidth);
        } else if (position <= 0.999f) { // (-1, 1)
            view.setAlpha(getAlpha(position));
            view.setTranslationX(pageWidth * -position);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setAlpha(0);
            view.setVisibility(View.GONE);
            view.setTranslationX(-pageWidth);
        }
    }

    private static final float getAlpha(final float position) {
        return getSlowQuadraticAlpha(position);
    }

    private static final float getLinearAlpha(final float position) {
        if (position <= 0) {
            return 1 + position;
        }
        return 1 - position;
    }

    private static final float getFastQuadraticAlpha(final float position) {
        final float linearAlpha = getLinearAlpha(position);
        return linearAlpha * linearAlpha;
    }

    private static final float getSlowQuadraticAlpha(final float position) {
        return 1 - position * position;
    }
}