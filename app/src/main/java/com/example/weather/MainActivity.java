package com.example.weather;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.Common.Constant;
import com.example.weather.Common.PreferenceHelper;
import com.example.weather.Model.City;
import com.example.weather.Model.DetailedList;
import com.example.weather.Model.WeatherDetails;
import com.example.weather.NetworkRequest.RequestInterface;
import com.example.weather.NetworkRequest.RetrofitController;
import com.example.weather.Utils.CheckInternetConnectivity;
import com.example.weather.Utils.Utils;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements LocationListener {
    private final int REQUEST_LOCATION_PERMISSION = 1;
    public RequestInterface requestInterface;
    private WeatherDetailsAdapter weatherDetailsAdapter;
    private RecyclerView recyclerView;
    private ArrayList<DetailedList> detailsArray = new ArrayList<>();
    private Boolean locationPermissionEnabled = false;
    private String longitude, latitude;
    private String locationPermission;
    private TextView warningMsg, txtViewCity,txtViewTemp;
    private City mCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
        checkPermission();
        if (locationPermissionEnabled) getLocation();
        PreferenceHelper.getInstance().intialization(this);
        if (PreferenceHelper.getInstance().getLatitude().equals(Utils.getConvertedDecimal(latitude))
                && PreferenceHelper.getInstance().getLongitude().equals(Utils.getConvertedDecimal(longitude))) {
            try {
                getCachedRetrofitData(this);
            } catch (IOException e) {
                e.printStackTrace();
                warningMsg.setVisibility(View.VISIBLE);
            }
        } else {
            requestAPI();
        }
        scheduleRepeatTask();
    }

    public void setUI() {
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        warningMsg = findViewById(R.id.warningMsg);
        TextView txtViewDay = findViewById(R.id.txtViewDay);
        TextView txtViewDate = findViewById(R.id.tctViewDate);
        txtViewCity = findViewById(R.id.txtViewCity);
        txtViewTemp = findViewById(R.id.txtViewTemp);
        Calendar sCalendar = Calendar.getInstance();
        String dayName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY", Locale.ENGLISH);
        String cDateTime = dateFormat.format(new Date());
        txtViewDay.setText(dayName);
        txtViewDate.setText(cDateTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceHelper.getInstance().intialization(this);
    }

    public void requestAPI() {
        if (latitude != null & longitude != null & CheckInternetConnectivity.checkConnectivity(getBaseContext())) {
            requestWeatherAPI(latitude, longitude);
            warningMsg.setVisibility(View.GONE);
        } else warningMsg.setVisibility(View.VISIBLE);
    }

    public void getCachedRetrofitData(Context context) throws IOException {
        File cacheFileDir = new File(context.getCacheDir(), Constant.DIRECTORY_NAME);
        if (cacheFileDir.exists()) {
            File[] files = cacheFileDir.getAbsoluteFile().listFiles();
            for (File file : files) {
                if (file.getName().endsWith(".1")) {
                    String filePath = String.valueOf(file.getAbsoluteFile());
                    FileInputStream fileInputStream = new FileInputStream(filePath);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String lineData = bufferedReader.readLine();
                    WeatherDetails weatherDetails = new Gson().fromJson(lineData, WeatherDetails.class);
                    detailsArray = weatherDetails.getList();
                    mCity = weatherDetails.getCity();
                    txtViewCity.setText(getResources().getString(R.string.city_country_name, mCity.getName(), mCity.getCountry()));
                    txtViewTemp.setText(getResources().getString(R.string.weather_units, detailsArray.get(0).getMain().getTemp()));
                    weatherDetailsAdapter = new WeatherDetailsAdapter(getBaseContext(), detailsArray);
                    recyclerView.setAdapter(weatherDetailsAdapter);
                    weatherDetailsAdapter.notifyDataSetChanged();
                }
            }
        } else {
            requestWeatherAPI(latitude, longitude);
            warningMsg.setVisibility(View.VISIBLE);
        }
    }

    public void requestWeatherAPI(final String lat, final String lon) {

        try {
            if (getCacheDir().exists()) {
                FileUtils.deleteDirectory(getCacheDir());
            }
        } catch (IOException ignored) {

        }
        requestInterface = RetrofitController.getInstance(this).create(RequestInterface.class);
        Call<WeatherDetails> calleque = requestInterface.getWeatherDetails(String.valueOf(lat), String.valueOf(lon), Constant.TEMP_UNITS, Constant.API_KEY);
        calleque.enqueue(new Callback<WeatherDetails>() {
            @Override
            public void onResponse(Call<WeatherDetails> call, Response<WeatherDetails> response) {
                if (CheckInternetConnectivity.checkConnectivity(getBaseContext())) {
                    if (response.body() != null) {
                        if (response.body().getCod().equals(Constant.SUCCESS_RESPONSE)) {
                            PreferenceHelper.getInstance().setLatitude(Utils.getConvertedDecimal(lat));
                            PreferenceHelper.getInstance().setLongitude(Utils.getConvertedDecimal(lon));
                            detailsArray = response.body().getList();
                            mCity = response.body().getCity();
                            txtViewCity.setText(getResources().getString(R.string.city_country_name, mCity.getName(), mCity.getCountry()));
                            txtViewTemp.setText(getResources().getString(R.string.weather_units, detailsArray.get(0).getMain().getTemp()));
                            weatherDetailsAdapter = new WeatherDetailsAdapter(getBaseContext(), detailsArray);
                            recyclerView.setAdapter(weatherDetailsAdapter);
                            weatherDetailsAdapter.notifyDataSetChanged();
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

    public void scheduleRepeatTask() {
        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Intent mIntent = new Intent(this, TaskSchedular.class);
        mIntent.putExtra(Constant.LATITUDE, latitude);
        mIntent.putExtra(Constant.LONGITUDE, longitude);
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000 * 120, alarmIntent);
    }

    public Boolean checkPermission() {
        locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
        //check Android Version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(locationPermission) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(locationPermission)) {
                    showDialogue(locationPermission, REQUEST_LOCATION_PERMISSION);
                } else {
                    requestPermissions(new String[]{locationPermission}, REQUEST_LOCATION_PERMISSION);
                }
                locationPermissionEnabled = false;
            } else locationPermissionEnabled = true;
        }
        return locationPermissionEnabled;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    public void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String mprovider = locationManager.getBestProvider(criteria, false);
        if (mprovider != null && !mprovider.equals("")) {
            if (checkPermission()) {
                Location location = locationManager.getLastKnownLocation(mprovider);
                locationManager.requestLocationUpdates(mprovider, 15000, 1, this);
                if (location != null) onLocationChanged(location);
                else
                    Toast.makeText(this, getResources().getText(R.string.perm_warning), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Check For Location
                getLocation();
            }
        } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(locationPermission)) {
                    showDialogue(locationPermission, requestCode);
                } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions)) {
                    showDialogue(locationPermission, requestCode);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showDialogue(final String storagePermission, final int code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.perm_title));
        builder.setMessage(getResources().getString(R.string.perm_msg));
        builder.setPositiveButton(getResources().getString(R.string.grant_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{storagePermission}, code);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.perm_warning), Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }


}
