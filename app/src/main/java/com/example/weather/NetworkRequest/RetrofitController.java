package com.example.weather.NetworkRequest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.weather.Common.Constant;
import com.example.weather.Model.DetailedList;
import com.example.weather.Model.WeatherDetails;
import com.example.weather.TaskSchedular;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("ALL")
public class RetrofitController {

    private ArrayList<DetailedList> weatherArray = new ArrayList<DetailedList>();
    public RequestInterface requestInterface;
    private static Cache cache;
    private RecyclerView recyclerView;
    private TaskSchedular taskSchedular;
    public static Retrofit retrofit = null;

    public static Retrofit getInstance(Context context) {
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder().cache(provideCacheDirectory(context)).addNetworkInterceptor(cacheInterCaptor()).addInterceptor(offlineStoredCacheData()).build();
            retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).client(httpClient).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Cache provideCacheDirectory(Context context) {
        File cacheDirectory = new File(context.getCacheDir(), Constant.DIRECTORY_NAME);
        cache = new Cache(cacheDirectory, Constant.CACHE_SIZE);
        return cache;
    }

    public static Interceptor cacheInterCaptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                int maxAge = 60 * 60 * 2;
                request.newBuilder().header(Constant.CACHE_CONTROL, "public, max-age=" + maxAge).removeHeader(Constant.PRAGMA).removeHeader(Constant.CACHE_CONTROL).build();
                return chain.proceed(request);
            }
        };
    }

    public static Interceptor offlineStoredCacheData() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                int maxStale = 60 * 60 * 2; // Offline cache available for 2 hours
                request.newBuilder().header(Constant.CACHE_CONTROL, "public, only-if-cached, max-stale=" + maxStale).removeHeader(Constant.PRAGMA).removeHeader(Constant.CACHE_CONTROL).build();
                return chain.proceed(request);
            }
        };
    }



}
