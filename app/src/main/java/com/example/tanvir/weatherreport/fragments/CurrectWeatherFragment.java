package com.example.tanvir.weatherreport.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.weatherreport.Activity.MainActivity;
import com.example.tanvir.weatherreport.R;
import com.example.tanvir.weatherreport.RetrofitClient;
import com.example.tanvir.weatherreport.WeatherApi;
import com.example.tanvir.weatherreport.models.weather_models.Weather;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrectWeatherFragment extends Fragment {

    WeatherApi weatherApi;


    TextView tempText, dateText, dayText, cityText, conditionText, minText, maxText,
            sunriseText, sunsetText, humidityText, pressureText;
    ImageView conditionImage;


    public interface OnFragmentInteractionListener {

    }

    public CurrectWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_currect_weather, container, false);

        tempText = view.findViewById(R.id.tempTexview);
        dateText = view.findViewById(R.id.dateTextview);
        dayText = view.findViewById(R.id.dayTextview);
        cityText = view.findViewById(R.id.cityNameTextview);

        conditionImage = view.findViewById(R.id.conditionImageview);
        conditionText = view.findViewById(R.id.conditionTextview);

        minText = view.findViewById(R.id.minTextview);
        maxText = view.findViewById(R.id.maxTextview);


        sunriseText = view.findViewById(R.id.sunriseTextview);
        sunsetText = view.findViewById(R.id.sunsetTextview);

        humidityText = view.findViewById(R.id.humidityTextview);
        pressureText = view.findViewById(R.id.pressureTextview);

        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);

        final Call<Weather> getWetherCall = weatherApi.getWeather();

        getWetherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();

                double temparature = (double)(weather.getMain().getTemp() - 273.15);
                tempText.setText(Double.toString(temparature)+ " " + "\u2103");
                double min = (double)(weather.getMain().getTempMin() - 273.15);
                minText.setText(Double.toString(min)+ " " + "\u2103");
                double max = (double)(weather.getMain().getTempMax() - 273.15);
                maxText.setText(Double.toString(max)+ " " + "\u2103");


                dateText.setText(dateFormate(weather.getDt()));
                dayText.setText(dayFormate(weather.getDt()));
                cityText.setText(weather.getName());



            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

        return  view;
    }

    public void OnQueryTextListener(String text) {
        Log.d("Tanvir",""+text);
    }


    // date formate
    private String dateFormate(int dt) {
        Date date = new java.util.Date(dt*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM dd, yyyy");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
    // get current day
    private String dayFormate(int dt) {
        Date date = new java.util.Date(dt*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
