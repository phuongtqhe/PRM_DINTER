package com.example.Dinter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPref {
    private final Context context;

    public SharedPref(Context context2) {
        this.context = context2;
    }

    public String readString(String str, String str2) {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getString(str, str2);
    }

    public void saveString(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this.context).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public int readInteger(String str, int number) {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getInt(str, number);
    }

    public void saveInteger(String str, int number) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this.context).edit();
        edit.putInt(str, number);
        edit.apply();
    }

    public boolean readBoolean(String str, Boolean bool) {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getBoolean(str, bool);
    }

    public void saveBoolean(String str, Boolean bool) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this.context).edit();
        edit.putBoolean(str, bool);
        edit.apply();
    }
}
