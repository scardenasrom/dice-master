package com.herkiusdev.dicemaster.activity.rpg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.herkiusdev.dicemaster.R;
import com.herkiusdev.dicemaster.util.AnimatorUtils;
import com.ogaclejapan.arclayout.ArcLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_single_throw)
public class SingleThrowActivity extends AppCompatActivity {

    @ViewById(R.id.single_throw_toolbar)
    Toolbar toolbar;
    @ViewById(R.id.single_throw_toolbar_title)
    TextView toolbarTitle;

    @ViewById(R.id.single_throw_dice_button)
    ImageButton singleThrowButton;
    @ViewById(R.id.single_throw_dice_menu)
    View diceMenu;
    @ViewById(R.id.single_throw_dice_arc)
    ArcLayout diceArc;

    @ViewById(R.id.single_throw_result_text)
    TextView throwResult;

    @AfterViews
    public void initViews(){
        setupToolbar();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(getText(R.string.single_throw_name));
    }

    @Click(R.id.single_throw_toolbar_back)
    public void goBack(){
        finish();
    }

    @Override
    public void onBackPressed() {
        if (singleThrowButton.isSelected()) {
            hideDiceMenu();
            singleThrowButton.setSelected(!singleThrowButton.isSelected());
        } else {
            finish();
        }
    }

    @Click(R.id.single_throw_dice_button)
    public void singleThrowClick() {
        if (singleThrowButton.isSelected()) {
            hideDiceMenu();
        } else {
            showDiceMenu();
        }
        singleThrowButton.setSelected(!singleThrowButton.isSelected());
    }

    //region Animations
    private void showDiceMenu() {
        diceMenu.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = diceArc.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(diceArc.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    private void hideDiceMenu(){
        List<Animator> animList = new ArrayList<>();

        for (int i = diceArc.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(diceArc.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                diceMenu.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();
    }

    private Animator createShowItemAnimator(View item) {
        float dx = singleThrowButton.getX() - item.getX();
        float dy = singleThrowButton.getY() - item.getY();

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

    private Animator createHideItemAnimator(final View item) {
        float dx = singleThrowButton.getX() - item.getX();
        float dy = singleThrowButton.getY() - item.getY();

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
    //endregion

    @Click(R.id.single_throw_d2)
    public void throw2Dice() {
        throwDice(2);
    }

    @Click(R.id.single_throw_d3)
    public void throw3Dice() {
        throwDice(3);
    }

    @Click(R.id.single_throw_d4)
    public void throw4Dice() {
        throwDice(4);
    }

    @Click(R.id.single_throw_d6)
    public void throw6Dice() {
        throwDice(6);
    }

    @Click(R.id.single_throw_d8)
    public void throw8Dice() {
        throwDice(8);
    }

    @Click(R.id.single_throw_d10)
    public void throw10Dice() {
        throwDice(10);
    }

    @Click(R.id.single_throw_d12)
    public void throw12Dice() {
        throwDice(12);
    }

    @Click(R.id.single_throw_d20)
    public void throw20Dice() {
        throwDice(20);
    }

    @Click(R.id.single_throw_d100)
    public void throw100Dice() {
        throwDice(100);
    }

    protected void throwDice(int sides) {
        int resultInt = ThreadLocalRandom.current().nextInt(1, sides + 1);
        String result = getString(R.string.single_throw_result, "" + resultInt);
        throwResult.setText(result);
    }

}