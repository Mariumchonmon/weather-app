package com.example.weather.common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class common {
    public static final  String API_ID = "59ec3c6e8ba9659f5f901b8b454a523f";
    public static Location current_location = null;

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mn dd EEE MM yyyy");
         String formatted = sdf.format(date);
         return formatted;

    }

    public static String convertUnixToHour(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mn");
        String formatted = sdf.format(date);
        return formatted;
    }
}
