package com.example.weather.NetworkRequest;

        import com.example.weather.Common.Constant;
        import com.example.weather.Model.WeatherDetails;

        import java.util.List;

        import okhttp3.Response;
        import okhttp3.ResponseBody;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.http.GET;
        import retrofit2.http.Path;
        import retrofit2.http.Query;
        import retrofit2.http.Url;

public interface RequestInterface {


    @GET(Constant.END_URL)
    Call<WeatherDetails> getWeatherDetails(@Query(Constant.LAT_PARAMETER) String lat, @Query(Constant.LON_PARAMETER) String lon, @Query(Constant.UNITS_PARAMETER) String units, @Query(Constant.APPID_PARAMETER) String appID);

    @GET
    Call<ResponseBody> fetchImage(@Url String url);
}
