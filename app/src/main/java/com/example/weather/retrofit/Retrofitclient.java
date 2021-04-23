package com.example.weather.retrofit;
import retrofit2.Retrofit;
import hu.akarnokd.rxjava3.retrofit.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitclient {
    private static Retrofit instance;

    public static Retrofit getInstance() {

        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        return instance;
    }
}

