package com.herkiusdev.dicemaster.activity.rpg;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_multiple_throws)
public class MultipleThrowsActivity extends AppCompatActivity {

    @ViewById(R.id.multiple_throws_toolbar)
    Toolbar toolbar;
    @ViewById(R.id.multiple_throws_toolbar_title)
    TextView toolbarTitle;

    @ViewById(R.id.multiple_throws_rolls_list)
    RecyclerView rollRecyclerView;

    boolean doubleBack = false;


    @AfterViews
    public void initViews() {
        setupToolbar();
        setupRecyclerView();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (rollRecyclerView.getAdapter().getItemCount() > 0) {
            if (doubleBack) {
                super.onBackPressed();
                return;
            }

            this.doubleBack = true;
            Snackbar.make(toolbar, R.string.several_players_go_back, Snackbar.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBack = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(getText(R.string.multiple_throws_name));
    }

    @Click(R.id.multiple_throws_toolbar_back)
    public void goBack() {
        finish();
    }

    private void setupRecyclerView() {

    }

    @Click(R.id.multiple_throws_add_roll)
    public void addNewRoll() {

    }

}
