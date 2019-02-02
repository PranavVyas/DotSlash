package com.teamzero.easyedu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SharedPrefsUtils {

    public static final String SHARED_PREF_FOLLOW_LIST = "FOLLOW_LIST";

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefsUtils(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setFollowList(String title, List<String> followList) {
        editor.putStringSet(SHARED_PREF_FOLLOW_LIST + "_" + title, new HashSet<>(followList));
        editor.apply();
    }

    public List<String> getFollowList(String title) {
        List<String> followList = new ArrayList<>(sharedPreferences.getStringSet(SHARED_PREF_FOLLOW_LIST + "_" + title, new HashSet<String>()));
        if (followList.isEmpty()) {
            return new ArrayList<>();
        } else {
            return followList;
        }
    }

}
