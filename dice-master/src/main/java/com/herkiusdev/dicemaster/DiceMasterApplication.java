package com.herkiusdev.dicemaster;

import android.app.Application;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@EApplication
public class DiceMasterApplication extends Application {

    @Pref
    AppPreferences_ preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

}
