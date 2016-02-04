package com.herkiusdev.dicemaster.activity.rpg;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.herkiusdev.dicemaster.R;
import com.herkiusdev.dicemaster.service.AnimatorService;
import com.ogaclejapan.arclayout.ArcLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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
    @ViewById(R.id.critics_and_fumbles_card_top_row)
    RelativeLayout cardTopRow;
    @ViewById(R.id.critics_and_fumbles_card_title)
    TextView cardTitle;
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

    private AnimatorService animService;

    @AfterViews
    public void initViews(){
        setupToolbar();
        animService = AnimatorService.getInstance();
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
            animService.hideMenu(criticsMenu, criticsArc, criticButton);
        } else {
            animService.showMenu(criticsMenu, criticsArc, criticButton);
        }
        criticButton.setSelected(!criticButton.isSelected());
    }

    @Click(R.id.critics_and_fumbles_fumble)
    public void fumbleClick() {
        if (fumbleButton.isSelected()) {
            animService.hideMenu(fumblesMenu, fumblesArc, fumbleButton);
        } else {
            animService.showMenu(fumblesMenu, fumblesArc, fumbleButton);
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

    @Click(R.id.critics_and_fumbles_sword_fumble)
    public void swordFumble() {
        deliverCrit(CRIT_SWORD, false);
    }

    @Click(R.id.critics_and_fumbles_hammer_fumble)
    public void hammerFumble() {
        deliverCrit(CRIT_HAMMER, false);
    }

    @Click(R.id.critics_and_fumbles_arrow_fumble)
    public void arrowFumble() {
        deliverCrit(CRIT_ARROW, false);
    }

    @Click(R.id.critics_and_fumbles_magic_fumble)
    public void magicFumble() {
        deliverCrit(FUMBLE_MAGIC, false);
    }

    //region Going Back
    @Override
    public void onBackPressed() {
        if (criticCard.getVisibility() == View.VISIBLE) {
            criticCard.setVisibility(View.GONE);
        } else {
            if (criticButton.isSelected()) {
                animService.hideMenu(criticsMenu, criticsArc, criticButton);
                criticButton.setSelected(!criticButton.isSelected());
            } else {
                if (fumbleButton.isSelected()) {
                    animService.hideMenu(fumblesMenu, fumblesArc, fumbleButton);
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

    private void deliverCrit(final int critType, final boolean isCritical) {
        // Set the card visible
        criticCard.setVisibility(View.VISIBLE);
        // Load the Card button:
        if (isCritical) {
            cardButton.setBackgroundResource(R.drawable.button_critical_card);
            switch (critType) {
                case CRIT_SWORD:
                    cardButton.setImageResource(R.drawable.ic_sword_original_orange);
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
            cardButton.setBackgroundResource(R.drawable.button_fumble_card);
            switch (critType) {
                case FUMBLE_SWORD:
//                    cardButton.setImageResource(R.drawable.ic_sword_blue);
                    break;
                case FUMBLE_HAMMER:
//                    cardButton.setImageResource(R.drawable.ic_hammer_blue);
                    break;
                case FUMBLE_ARROW:
//                    cardButton.setImageResource(R.drawable.ic_arrow_blue);
                    break;
                case FUMBLE_MAGIC:
//                    cardButton.setImageResource(R.drawable.ic_magic_blue);
                    break;
            }
        }
        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverCrit(critType, isCritical);
            }
        });
        // Load the Card title:
        if (isCritical) {
            cardTopRow.setBackgroundColor(getResources().getColor(R.color.color_primary));
            cardTitle.setText("Pulmón Perforado");
        } else {
            cardTopRow.setBackgroundColor(getResources().getColor(R.color.color_accent));
            cardTitle.setText("Deflagración Arcana");
        }
    }

}
