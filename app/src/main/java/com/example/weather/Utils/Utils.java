package com.example.weather.Utils;
/**
 * Created by NileshPatel on 2019-05-11.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.weather.Common.Constant;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static String mTime;

    public static String getConvertedTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
        Date newDate = null;
        try {
            newDate = format.parse(time);
            format = new SimpleDateFormat(Constant.DISPLAY_DATE);
            mTime = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mTime;
    }

    public static String getConvertedDecimal(String key) {
        String mkey = null;
        try {
            DecimalFormat formater = new DecimalFormat("#");
            mkey = formater.format(Double.valueOf(key));
        } catch (NullPointerException e) {
            //DO nothing
        }
        return mkey;
    }

    public static Boolean checkConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            return activeNetwork.isConnected() & activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        } else return false;
    }


}
