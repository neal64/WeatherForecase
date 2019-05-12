package com.example.weather.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("id")
    @Expose
    private String id;

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getMain() {
        return main;
    }

    public String getId() {
        return id;
    }


}


