package com.herkiusdev.dicemaster.service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.herkiusdev.dicemaster.util.AnimatorUtils;
import com.ogaclejapan.arclayout.ArcLayout;

import java.util.ArrayList;
import java.util.List;

public class AnimatorService {

    public static final int SHOW_DURATION = 350;
    public static final int HIDE_DURATION = 350;

    private static AnimatorService instance = null;

    protected AnimatorService() {

    }

    public static AnimatorService getInstance() {
        if (instance == null) {
            instance = new AnimatorService();
        }
        return instance;
    }

    public void showMenu(View menu, ArcLayout arc, View centerView) {
        menu.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = arc.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arc.getChildAt(i), centerView));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(SHOW_DURATION);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    private Animator createShowItemAnimator(View item, View centerItem) {
        float dx = centerItem.getX() - item.getX();
        float dy = centerItem.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }

    public void hideMenu(final View menu, ArcLayout arc, View centerItem) {
        List<Animator> animList = new ArrayList<>();

        for (int i = arc.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arc.getChildAt(i), centerItem));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(HIDE_DURATION);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menu.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();
    }

    private Animator createHideItemAnimator(final View item, View centerItem) {
        float dx = centerItem.getX() - item.getX();
        float dy = centerItem.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }

}
