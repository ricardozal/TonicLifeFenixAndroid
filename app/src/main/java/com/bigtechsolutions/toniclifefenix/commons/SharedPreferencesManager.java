package com.bigtechsolutions.toniclifefenix.commons;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";

    private SharedPreferencesManager() {}

    private static SharedPreferences getSharedPreferences() {

        return MyFenixApp.getContext()
                .getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);

    }

    public static void setStringValue(String key, String value)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void setIntegerValue(String key, int value)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static String getStringValue(String key)
    {
        return getSharedPreferences().getString(key, null);
    }

    public static Integer getIntValue(String key)
    {
        return getSharedPreferences().getInt(key, 0);
    }

    public static void removeValue(String key)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        editor.commit();
    }

}
