package com.example.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.weather.Model.DetailedList;
import com.example.weather.Model.Main;
import com.example.weather.Model.Weather;
import com.example.weather.Model.Wind;
import com.example.weather.Utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class WeatherDetailsAdapter extends RecyclerView.Adapter<WeatherDetailsAdapter.MyViewHolder> {
    private ArrayList<DetailedList> weatherDetailsList = new ArrayList<>();
    private Context context;

    WeatherDetailsAdapter(Context context, ArrayList<DetailedList> weatherDetailsList) {
        this.weatherDetailsList = weatherDetailsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_list_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {
        DetailedList weatherDetails = weatherDetailsList.get(position);
        final Main mainModel = weatherDetailsList.get(position).getMain();
        ArrayList<Weather> weatherArray = weatherDetailsList.get(position).getWeather();
        Wind windModel = weatherDetailsList.get(position).getWind();
        myViewHolder.txtViewTime.setText(Utils.getConvertedTime(weatherDetails.getDt_txt()));
        Picasso.with(context).load(context.getResources().getString(R.string.main_humidity, weatherArray.get(0).getIcon())).into(myViewHolder.imgIcon);
        myViewHolder.txtViewTemp.setText(context.getResources().getString(R.string.main_temp, mainModel.getTemp()));
        myViewHolder.txtViewMinTemp.setText(context.getResources().getString(R.string.main_temp, mainModel.getTemp_min()));
        myViewHolder.txtViewMaxTemp.setText(context.getResources().getString(R.string.main_temp, mainModel.getTemp_max()));
        myViewHolder.txtViewHumidity.setText(context.getResources().getString(R.string.str_humidity, mainModel.getHumidity()));
        myViewHolder.txtViewWind.setText(context.getResources().getString(R.string.str_wind, windModel.getSpeed()));
        myViewHolder.txtViewPressure.setText(context.getResources().getString(R.string.str_pressure, mainModel.getPressure()));
        myViewHolder.txtViewSeaLevel.setText(context.getResources().getString(R.string.str_sea_level,mainModel.getSea_level()));
    }

    @Override
    public int getItemCount() {
        return weatherDetailsList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtViewTime,txtViewSeaLevel,txtViewTemp, txtViewMinTemp, txtViewMaxTemp, txtViewPressure, txtViewHumidity, txtViewWind;
        private ImageView imgIcon;
        private LinearLayout lyt_moreDetails;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTime = itemView.findViewById(R.id.txtViewTime);
            txtViewTemp = itemView.findViewById(R.id.txtViewTemp);
            imgIcon = itemView.findViewById(R.id.tempIcon);
            txtViewMinTemp = itemView.findViewById(R.id.txtViewMinTemp);
            txtViewMaxTemp = itemView.findViewById(R.id.txtViewMaxTemp);
            txtViewPressure = itemView.findViewById(R.id.txtViewPressure);
            txtViewHumidity = itemView.findViewById(R.id.txtViewHumidity);
            txtViewWind = itemView.findViewById(R.id.txtViewWind);
            lyt_moreDetails = itemView.findViewById(R.id.lyt_moreDetails);
            txtViewSeaLevel = itemView.findViewById(R.id.txtViewSeaLevel);

        }
    }
}
