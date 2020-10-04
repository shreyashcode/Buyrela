package com.example.firestore.Modal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs
{
    private SharedPreferences preferences;
    public static final String USER_DETAILS = "USER_DETAILS";
    public Prefs(Context context)
    {
        this.preferences = context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
    }

    public void setUserName(String userName)
    {
        preferences.edit().putString("userName_id", userName).apply();
    }

    public void setUserPhone(String phone)
    {
        preferences.edit().putString("userPhone_id", phone).apply();
    }

    public String getUserName()
    {
        return preferences.getString("userName_id", "noUser");
    }

    public String getUserPhone()
    {
        return preferences.getString("userPhone_id", "noPhone");
    }
}
