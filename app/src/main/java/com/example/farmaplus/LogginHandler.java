package com.example.farmaplus;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class LogginHandler {

    public static Boolean isLoggedIn(Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    public static UserType getUserType(Activity activity){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        try {
            UserType userType = UserType.valueOf(sharedPreferences.getString("userType", ""));
            return userType;
        } catch (Exception e) {
            return null;
        }
    }

    public static void setLoggedIn(Activity activity, Boolean logged, UserType userType, String id) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", logged);
        editor.putString("userType", userType.toString());
        editor.putString("idUser", id);
        editor.apply();
    }
}
