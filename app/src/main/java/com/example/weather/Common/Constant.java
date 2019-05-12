package com.example.weather.Common;

import android.content.SharedPreferences;

public class Constant {

    //Retrofit class Constant
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static final String END_URL = "forecast/hourly";
    public static final String LAT_PARAMETER = "lat";
    public static final String LON_PARAMETER = "lon";
    public static final String UNITS_PARAMETER = "units";
    public static final String APPID_PARAMETER = "appid";
    public static final String ICON_LOAD_BASE_URL = "http://openweathermap.org/img/";
    public static final String PRAGMA = "Pragma";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final int CACHE_SIZE = 15 * 1024 * 1024;
    public static final String API_KEY = "5ad7218f2e11df834b0eaf3a33a39d2a";
    public static final String TEMP_UNITS ="imperial";
    public static final String SUCCESS_RESPONSE = "200";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String SET_LATITUDE = "setLatitude";
    public static final String SET_LONGITUDE = "setLongitude";
    public static final String DATE_FORMAT = "YYYY-MM-DD HH:mm:ss";
    public static final String DISPLAY_DATE = "hh:mm a";
    public static final String DIRECTORY_NAME = "weatherCall";
}
