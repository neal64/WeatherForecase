package com.example.weather.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import okhttp3.Interceptor;

public class CheckInternetConnectivity {

    public static Boolean checkConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            return activeNetwork.isConnected() & activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        }else return false;
    }

}
