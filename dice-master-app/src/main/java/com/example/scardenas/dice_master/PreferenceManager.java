package com.example.scardenas.dice_master;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.scardenas.dice_master.data.Roll;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreferenceManager {

    private static final String PREFS_NAME = "preference-general";
    private static final String PREF_FAVOURITE_ROLLS = "favourite-rolls";
    private static final String PREF_HISTORY_ROLLS = "history-rolls";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        gson = new Gson();
    }

    public List<Roll> getFavouriteRolls() {
        return getRolls(PREF_FAVOURITE_ROLLS);
    }

    public List<Roll> getHistoryRolls() {
        return getRolls(PREF_HISTORY_ROLLS);
    }

    private List<Roll> getRolls(String whatRolls) {
        List<Roll> rollList = new ArrayList<>();
        Set<String> rollStringSet = prefs.getStringSet(whatRolls, new HashSet<String>(0));
        if (rollStringSet.size() > 0) {
            for (String rollString: rollStringSet) {
                Roll roll = gson.fromJson(rollString, Roll.class);
                rollList.add(roll);
            }
        }
        return rollList;
    }

    public void setFavouriteRolls(List<Roll> rollList) {
        setRolls(PREF_FAVOURITE_ROLLS, rollList);
    }

    public void setHistoryRolls(List<Roll> rollList) {
        setRolls(PREF_HISTORY_ROLLS, rollList);
    }

    private void setRolls(String whatRolls, List<Roll> rollList) {
        Set<String> rollStringSet = new HashSet<>(rollList.size());
        for (Roll roll: rollList) {
            String rollString = gson.toJson(roll, Roll.class);
            rollStringSet.add(rollString);
        }
        editor.putStringSet(whatRolls, rollStringSet).apply();
    }

}
