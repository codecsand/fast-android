package com.mahmz.android.Classes;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 * Created by Mah on 3/18/2016.
 */
public class Func {
    private static final String TAG = "";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static Drawable getDrawableImage(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, context.getTheme());
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    public static void saveValue(String key, String value, Activity activity) {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readValue(String key, Activity activity) {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String value = app_preferences.getString(key, "");
        return value;
    }

    public static String randomKey() {
        // TODO Auto-generated method stub
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
