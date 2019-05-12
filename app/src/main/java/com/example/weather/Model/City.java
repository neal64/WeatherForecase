package com.example.weather.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City
{
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("population")
    @Expose
    private String population;

    public String getCountry ()
    {
        return country;
    }

    public String getName ()
    {
        return name;
    }

    public String getId ()
    {
        return id;
    }

    public String getPopulation ()
    {
        return population;
    }


}

