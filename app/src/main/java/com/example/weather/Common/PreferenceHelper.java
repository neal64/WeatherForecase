package com.example.weather.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceHelper {
    private Context mContext;
    private static PreferenceHelper mInstance;
    private SharedPreferences mMySharedPreferences;

    public static PreferenceHelper getInstance() {
        if (mInstance == null) {
            mInstance = new PreferenceHelper();
        }
        return mInstance;
    }

    public void intialization(Context context) {
        mContext = context;
        mMySharedPreferences = context.getSharedPreferences("WeatherAndroid", Activity.MODE_PRIVATE);
    }

    public void setLatitude(String lat) {
        SharedPreferences.Editor editor = mMySharedPreferences.edit();
        editor.putString(Constant.SET_LATITUDE, lat);
        editor.apply();
    }

    public String getLatitude() {
        return mMySharedPreferences.getString(Constant.SET_LATITUDE, "value");
    }

    public void setLongitude(String lon) {
        SharedPreferences.Editor editor = mMySharedPreferences.edit();
        editor.putString(Constant.SET_LONGITUDE, lon);
        editor.apply();
    }

    public String getLongitude() {
        return mMySharedPreferences.getString(Constant.SET_LONGITUDE, "value");
    }
}
