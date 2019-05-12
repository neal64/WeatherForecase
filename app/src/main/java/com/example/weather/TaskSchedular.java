package com.example.weather;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.weather.Common.Constant;
import com.example.weather.Common.PreferenceHelper;
import com.example.weather.Model.WeatherDetails;
import com.example.weather.NetworkRequest.RequestInterface;
import com.example.weather.NetworkRequest.RetrofitController;
import com.example.weather.Utils.CheckInternetConnectivity;
import com.example.weather.Utils.Utils;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskSchedular extends Service {
    public RequestInterface requestInterface;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        final Bundle bundle = intent.getExtras();
        assert bundle != null;
        try {
            requestWeatherAPI(bundle.getString(Constant.LATITUDE), bundle.getString(Constant.LONGITUDE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void requestWeatherAPI(final String lat, final String lon) throws IOException {

        if (CheckInternetConnectivity.checkConnectivity(this)) {
            if (getCacheDir().exists()) {
                FileUtils.deleteDirectory(getCacheDir());
            }
            requestInterface = RetrofitController.getInstance(this).create(RequestInterface.class);
            Call<WeatherDetails> calleque = requestInterface.getWeatherDetails(lat, lon, Constant.TEMP_UNITS, Constant.API_KEY);
            calleque.enqueue(new Callback<WeatherDetails>() {
                @Override
                public void onResponse(Call<WeatherDetails> call, Response<WeatherDetails> response) {
                    if (CheckInternetConnectivity.checkConnectivity(getBaseContext())) {
                        if (response.body() != null) {
                            if (response.body().getCod().equals(Constant.SUCCESS_RESPONSE)) {
                                PreferenceHelper.getInstance().setLatitude(Utils.getConvertedDecimal(lat));
                            } else {
                                Toast.makeText(getBaseContext(), getResources().getText(R.string.msg_api_error, response.body().getCod()), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<WeatherDetails> call, Throwable t) {
                    Toast.makeText(getBaseContext(), getResources().getText(R.string.msg_call_failed, t.getMessage()), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
