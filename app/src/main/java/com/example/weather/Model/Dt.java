package com.example.weather.Model;

import android.view.LayoutInflater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Dt {
    @SerializedName("dt")
    @Expose
    private float dt;
    @SerializedName("Main")
    private List<Main> main = new ArrayList<>();
    @SerializedName("Clouds")
    private List<Clouds> clouds;
    @SerializedName("Wind")
    private List<Wind> wind;
    @SerializedName("Sys")
    private List<Sys> sys;
    @SerializedName("dt_txt")
    @Expose
    private String dt_txt;


// Getter Methods

    public float getDt() {
        return dt;
    }

    public List<Main> getMain() {
        return main;
    }

    public List<Clouds> getClouds() {
        return clouds;
    }

    public List<Wind> getWind() {
        return wind;
    }

    public List<Sys> getSys() {
        return sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }

// Setter Methods

    public void setDt(float dt) {
        this.dt = dt;
    }

    public void setMain(List<Main> mainObject) {
        this.main = main;
    }

    public void setClouds(List<Clouds> cloudsObject) {
        this.clouds = clouds;
    }

    public void setWind(List<Wind> windObject) {
        this.wind = wind;
    }

    public void setSys(List<Sys> sysObject) {
        this.sys = sys;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}