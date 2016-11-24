package com.example.root.diabetesapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GERARDOMOXCARUIZ on 02/11/2016.
 */

public class SharedPreference {
    public static final String PREFS_NAME = "Session";

    public SharedPreference() {
        super();
    }

    public void save(Context context, String key, String value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(key, value);

        editor.apply();
    }

    public String getValue(Context context, String key) {
        SharedPreferences settings;
        String text;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(key, null);
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.apply();
    }

    public void removeValue(Context context, String key) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(key);
        editor.apply();
    }

    public Boolean exists(Context context, String key){
        SharedPreferences settings;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return settings.contains(key);

    }

    public String all(Context context){
        SharedPreferences settings;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return settings.getAll().toString();
    }
}