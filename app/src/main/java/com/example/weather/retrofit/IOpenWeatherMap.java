package com.example.weather.retrofit;

import com.example.weather.Model.WeatherForecastResult;
import com.example.weather.Model.WeatherResult;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lan,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);
    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLng(@Query("lat") String lat,
                                                                 @Query("lon") String lan,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String unit);
}
