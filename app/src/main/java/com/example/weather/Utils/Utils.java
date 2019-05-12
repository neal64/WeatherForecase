package com.example.weather.Utils;

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
        }catch (NullPointerException e){
            //DO nothing
        }
        return mkey;
    }


}
