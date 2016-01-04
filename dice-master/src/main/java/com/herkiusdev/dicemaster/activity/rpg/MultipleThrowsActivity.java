package com.herkiusdev.dicemaster.activity.rpg;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
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

    @AfterViews
    public void initViews() {
        setupToolbar();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(getText(R.string.multiple_throws_name));
    }

    @Click(R.id.multiple_throws_toolbar_back)
    public void goBack() {
        finish();
    }

}
