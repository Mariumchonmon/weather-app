package com.example.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.Model.WeatherResult;
import com.example.weather.common.common;
import com.example.weather.retrofit.IOpenWeatherMap;
import com.example.weather.retrofit.Retrofitclient;
import com.google.android.gms.common.internal.service.Common;
import com.squareup.picasso.Picasso;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.example.weather.R.id.img_weather;
import static com.example.weather.R.id.txt_geo_coord;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayWeatherFragment extends Fragment {

    ImageView img_weather;
    TextView txt_city_name, txt_temperature, txt_description, txt_date_time, txt_wind, txt_pressure, txt_humidity,
            txt_sunrise, txt_sunset, txt_geo_coord;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    static TodayWeatherFragment instance;
    public static TodayWeatherFragment getInstance() {
        if (instance == null)
            instance = new TodayWeatherFragment();
        return instance;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TodayWeatherFragment() {
     compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = Retrofitclient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayWeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayWeatherFragment newInstance(String param1, String param2) {
        TodayWeatherFragment fragment = new TodayWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_today_weather, container, false);


        img_weather = (ImageView)itemView.findViewById(R.id.img_weather);
        txt_city_name = (TextView)itemView.findViewById(R.id.txt_city_name);
        txt_description = (TextView)itemView.findViewById(R.id.txt_description);
        txt_geo_coord = (TextView)itemView.findViewById(R.id.txt_geo_coord);
        txt_humidity = (TextView)itemView.findViewById(R.id.txt_humidity);
        txt_pressure = (TextView)itemView.findViewById(R.id.txt_pressure);
        txt_sunrise = (TextView)itemView.findViewById(R.id.txt_sunrise);
        txt_sunset = (TextView)itemView.findViewById(R.id.txt_sunset);
        txt_wind = (TextView)itemView.findViewById(R.id.txt_wind);
        txt_temperature = (TextView)itemView.findViewById(R.id.txt_temperature);
        txt_date_time = (TextView)itemView.findViewById(R.id.txt_date_time);

        weather_panel = (LinearLayout)itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar)itemView.findViewById(R.id.loading);

        getWeatherInformation();

        return itemView;

    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(common.current_location.getLatitude()),
                String.valueOf(common.current_location.getLongitude()),
                common.API_ID,

                "metric")
                .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Consumer<WeatherResult>() {
                      @Override
                      public void accept(WeatherResult weatherResult) throws Exception {
                           //Load image
                          Picasso.get().load(new StringBuilder("/themes/openweathermap/assets/img/about_us/")
                                  .append(weatherResult.getWeather().get(0).getIcon())
                          .append(".png").toString()).into(img_weather);

                          //Load information

                          txt_city_name.setText(weatherResult.getName());
                          txt_description.setText(new StringBuilder("Weather in")
                          .append(weatherResult.getName()).toString());
                          txt_temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
                          txt_date_time.setText(common.convertUnixToDate(weatherResult.getDt()));
                          txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hpa").toString());
                          txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%").toString());
                          txt_sunrise.setText(common.convertUnixToHour(weatherResult.getSys().getSunrise()));
                          txt_sunset.setText(common.convertUnixToHour(weatherResult.getSys().getSunset()));
                         txt_geo_coord.setText(new StringBuilder("[").append(weatherResult.getCoord().toString()).append("]").toString());

                          //Display panel
                          weather_panel.setVisibility(View.VISIBLE);
                          loading.setVisibility(View.GONE);

                      }
                  }, new Consumer<Throwable>() {
                      @Override
                      public void accept(Throwable throwable) throws Exception {
                          Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();


                      }

    })
    );
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}