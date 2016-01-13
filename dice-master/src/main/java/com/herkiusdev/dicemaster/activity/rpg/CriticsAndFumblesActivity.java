package com.herkiusdev.dicemaster.activity.rpg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
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

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_critics_and_fumbles)
public class CriticsAndFumblesActivity extends AppCompatActivity{

    //region Toolbar
    @ViewById(R.id.critics_and_fumbles_toolbar)
    Toolbar toolbar;
    @ViewById(R.id.critics_and_fumbles_toolbar_title)
    TextView toolbarTitle;
    //endregion

    //region Card Elements
    @ViewById(R.id.critics_and_fumbles_card)
    CardView criticCard;
    @ViewById(R.id.critics_and_fumbles_card_top_button)
    ImageButton cardButton;
    //endregion

    //region Critics
    @ViewById(R.id.critics_and_fumbles_critic)
    Button criticButton;
    @ViewById(R.id.critics_and_fumbles_critics_menu)
    View criticsMenu;
    @ViewById(R.id.critics_and_fumbles_critics_arc)
    ArcLayout criticsArc;
    //endregion

    //region Fumbles
    @ViewById(R.id.critics_and_fumbles_fumble)
    Button fumbleButton;
    @ViewById(R.id.critics_and_fumbles_fumbles_menu)
    View fumblesMenu;
    @ViewById(R.id.critics_and_fumbles_fumbles_arc)
    ArcLayout fumblesArc;
    //endregion

    public static final int CRIT_SWORD = 1;
    public static final int CRIT_HAMMER = 2;
    public static final int CRIT_ARROW = 3;
    public static final int CRIT_MAGIC = 4;
    public static final int FUMBLE_SWORD = 5;
    public static final int FUMBLE_HAMMER = 6;
    public static final int FUMBLE_ARROW = 7;
    public static final int FUMBLE_MAGIC = 8;

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
        toolbarTitle.setText(getText(R.string.critics_and_fumbles_name));
    }

    @Click(R.id.critics_and_fumbles_critic)
    public void criticClick() {
        if (criticButton.isSelected()) {
            hideCriticsMenu();
        } else {
            showCriticsMenu();
        }
        criticButton.setSelected(!criticButton.isSelected());
    }

    @Click(R.id.critics_and_fumbles_fumble)
    public void fumbleClick() {
        if (fumbleButton.isSelected()) {
            hideFumblesMenu();
        } else {
            showFumblesMenu();
        }
        fumbleButton.setSelected(!fumbleButton.isSelected());
    }

    @Click(R.id.critics_and_fumbles_sword_crit)
    public void swordCrit(){
        deliverCrit(CRIT_SWORD, true);
    }

    @Click(R.id.critics_and_fumbles_hammer_crit)
    public void hammerCrit() {
        deliverCrit(CRIT_HAMMER, true);
    }

    @Click(R.id.critics_and_fumbles_arrow_crit)
    public void arrowCrit() {
        deliverCrit(CRIT_ARROW, true);
    }

    @Click(R.id.critics_and_fumbles_magic_crit)
    public void magicCrit() {
        deliverCrit(CRIT_MAGIC, true);
    }

    //region Animations
    private void showCriticsMenu(){
        criticsMenu.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = criticsArc.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimatorCritic(criticsArc.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    private void showFumblesMenu() {
        fumblesMenu.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = fumblesArc.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimatorFumble(fumblesArc.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    private void hideCriticsMenu(){
        List<Animator> animList = new ArrayList<>();

        for (int i = criticsArc.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimatorCritic(criticsArc.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                criticsMenu.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();
    }

    private void hideFumblesMenu() {
        List<Animator> animList = new ArrayList<>();

        for (int i = fumblesArc.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimatorFumble(fumblesArc.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fumblesMenu.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();
    }

    private Animator createShowItemAnimatorCritic(View item) {
        float dx = criticButton.getX() - item.getX();
        float dy = criticButton.getY() - item.getY();

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

    private Animator createShowItemAnimatorFumble(View item) {
        float dx = fumbleButton.getX() - item.getX();
        float dy = fumbleButton.getY() - item.getY();

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

    private Animator createHideItemAnimatorCritic(final View item) {
        float dx = criticButton.getX() - item.getX();
        float dy = criticButton.getY() - item.getY();

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

    private Animator createHideItemAnimatorFumble(final View item) {
        float dx = fumbleButton.getX() - item.getX();
        float dy = fumbleButton.getY() - item.getY();

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

    //region Going Back
    @Override
    public void onBackPressed() {
        if (criticCard.getVisibility() == View.VISIBLE) {
            criticCard.setVisibility(View.GONE);
        } else {
            if (criticButton.isSelected()) {
                hideCriticsMenu();
                criticButton.setSelected(!criticButton.isSelected());
            } else {
                if (fumbleButton.isSelected()) {
                    hideFumblesMenu();
                    fumbleButton.setSelected(!fumbleButton.isSelected());
                } else {
                    finish();
                }
            }
        }
    }

    @Click(R.id.critics_and_fumbles_toolbar_back)
    public void goBack(){
        finish();
    }
    //endregion

    private void deliverCrit(int critType, boolean isCritical) {
        // Set the card visible
        criticCard.setVisibility(View.VISIBLE);
        // Load the Card button:
        if (isCritical) {
            switch (critType) {
                case CRIT_SWORD:
                    cardButton.setImageResource(R.drawable.ic_sword_orange);
                    break;
                case CRIT_HAMMER:
                    cardButton.setImageResource(R.drawable.ic_hammer_orange);
                    break;
                case CRIT_ARROW:
                    cardButton.setImageResource(R.drawable.ic_arrow_orange);
                    break;
                case CRIT_MAGIC:
                    cardButton.setImageResource(R.drawable.ic_magic_orange);
                    break;
            }
        } else {

        }
    }

}
