package com.example.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.Model.WeatherForecastResult;
import com.example.weather.R;
import com.example.weather.common.common;
import com.google.android.gms.common.internal.service.Common;
import com.squareup.picasso.Picasso;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {
    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Load icon
        //Load image
        Picasso.get().load(new StringBuilder("/themes/openweathermap/assets/img/about_us/")
                .append(weatherForecastResult.list.get(position).weather.get(0).getIcon())
                .append(".png").toString()).into(holder.img_weather);

        holder.txt_date_time.setText(new StringBuilder(common.convertUnixToDate(weatherForecastResult
        .list.get(position).dt)));

        holder.txt_description.setText(new StringBuilder(weatherForecastResult.list.get(position)
                .weather.get(0).getDescription()));

        holder.txt_temperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position)
        .main.getTemp())).append("°C"));




    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date_time, txt_description, txt_temperature;
        ImageView img_weather;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_weather = (ImageView)itemView.findViewById(R.id.img_weather);
            txt_date_time = (TextView)itemView.findViewById(R.id.txt_date);
            txt_description = (TextView)itemView.findViewById(R.id.txt_description);
            txt_temperature = (TextView)itemView.findViewById(R.id.txt_temperature);
        }
    }


}
