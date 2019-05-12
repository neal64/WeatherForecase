package com.example.weather.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("deg")
    @Expose
    private String deg;
    @SerializedName("speed")
    @Expose
    private String speed;

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

}
