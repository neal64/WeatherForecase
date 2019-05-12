package com.example.weather.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DetailedList {
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("dt_txt")
    @Expose
    private String dt_txt;
    @SerializedName("weather")
    @Expose
    private ArrayList<Weather> weather = new ArrayList<>();
    @SerializedName("main")
    private Main main;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("wind")
    @Expose
    private Wind wind;

    public String getDt() {
        return dt;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }


    public Clouds getClouds() {
        return clouds;
    }


    public Sys getSys() {
        return sys;
    }


    public Wind getWind() {
        return wind;
    }


}