package com.coupanapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Util {
    public static String getStringPref(String key_val, String def_val, Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref_" + key_val, context.MODE_PRIVATE);
        return pref.getString(key_val, def_val);
    }

    public static void setStringPref(String key_val, String val, Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref_" + key_val, context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.clear();
        prefEditor.putString(key_val, val);
        prefEditor.commit();
    }
}
