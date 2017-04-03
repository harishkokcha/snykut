package com.namdev.sanyukt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.namdev.sanyukt.beans.Users;

/**
 * Created by Harish on 12/23/2016.
 */

public class AppPreferences {

    private static final String USER_NAME = "USER_NAME";
    private static final String USER_PHONE = "USER_PHONE";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_ID = "USER_ID";
    private static final String USER_DEVICE_ID = "USER_DEVICE_ID";
    public static AppPreferences mInstance;
    private final String LOGIN_FILE = "LoginSetting";
    public Context context;

    private AppPreferences() {

    }

    public static AppPreferences getInstance() {
        if (mInstance == null) {
            mInstance = new AppPreferences();
        }
        return mInstance;
    }

    public void putString(Context context, String key, String value) {
        try {

            SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE);
            Log.d("savePrefences", "Folder id sharedPreferences =" + key + "::" + value);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
            System.out.println("Exception in Creating Login File SharedPreference" + e.toString());
        }
    }

    public void putInt(Context context, String key, int value) {
        try {

            SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE);
            Log.d("savePrefences", "Folder id sharedPreferences =" + key + "::" + value);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.apply();
        } catch (Exception e) {
            System.out.println("Exception in Creating Login File SharedPreference" + e.toString());
        }
    }

    public String getString(Context context, String key) {
        String value = "";
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE);
            value = sharedPreferences.getString(key, "");
        } catch (Exception e) {
            System.out.println("Exception while Loading Prefernces" + e.toString());
        }
        return value;
    }

    public int getInt(Context context, String key) {
        int value = 0;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE);
            value = sharedPreferences.getInt(key, 0);
        } catch (Exception e) {
            System.out.println("Exception while Loading Prefernces" + e.toString());
        }
        return value;
    }

    public void remove(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public boolean contains(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.contains(key);
    }

    public void setUserLogin(Context context, Users users) {
        putString(context, USER_NAME, users.getUsername());
        putString(context, USER_PHONE, users.getUserphoneno());
        putString(context, USER_EMAIL, users.getUseremail());
        putString(context, USER_ID, users.getUserid());
        Log.d("Harish","setUserLogin AppPreferences : "+users.getUserid());
        Log.d("Harish","setUserLogin AppPreferences getUser(context).getUserid() : "+getUser(context).getUserid());
    }

    public Users getUser(Context context) {
        Users users = new Users();
        users.setUsername(getString(context, USER_NAME));
        users.setUserid(getString(context, USER_ID));
        users.setUseremail(getString(context, USER_EMAIL));
        users.setUserphoneno(getString(context, USER_PHONE));
        return users;
    }

    public void setDeviceId(String refreshedToken) {
        putString(context, USER_DEVICE_ID, refreshedToken);
    }


    public String getDeviceId(Context context) {
        return getString(context, USER_DEVICE_ID);
    }
}
