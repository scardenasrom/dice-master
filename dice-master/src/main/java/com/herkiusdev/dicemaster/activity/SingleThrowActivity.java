package com.herkiusdev.dicemaster.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_single_throw)
public class SingleThrowActivity extends AppCompatActivity{

    @AfterViews
    public void initViews() {
        getSupportActionBar().setTitle("Dice Master - Tirada Única");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Click(R.id.single_throw_d20_btn)
    public void d20IconClick(){
        Toast.makeText(SingleThrowActivity.this, "Dice pressed!", Toast.LENGTH_SHORT).show();
    }

    @OptionsItem(android.R.id.home)
    public void goBack() {
        finish();
    }

}
