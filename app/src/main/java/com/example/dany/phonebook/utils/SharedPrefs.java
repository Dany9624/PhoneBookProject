package com.example.dany.phonebook.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dany on 13.10.2018 г..
 */

public class SharedPrefs {

    private static final String NAME = "SharedPrefs";
    private static final String INITIAL_LAUNCH = "initialLaunch";

    public static void setInitialLaunch(Context context, boolean initialLaunch)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(INITIAL_LAUNCH, initialLaunch).apply();
    }

    public static boolean getInitialLaunch(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(INITIAL_LAUNCH, true);
    }
}
