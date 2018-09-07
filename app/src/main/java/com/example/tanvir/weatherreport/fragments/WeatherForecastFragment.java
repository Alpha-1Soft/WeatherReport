package com.example.tanvir.weatherreport.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tanvir.weatherreport.Adapters.ForecastAdapter;
import com.example.tanvir.weatherreport.R;
import com.example.tanvir.weatherreport.RetrofitClient;
import com.example.tanvir.weatherreport.WeatherApi;
import com.example.tanvir.weatherreport.models.needy_models.Forecast;
import com.example.tanvir.weatherreport.models.weather_models.Weather;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherForecastFragment extends Fragment {
    WeatherApi weatherApi;
    ListView listView;
    ArrayAdapter arrayAdapter;

    public interface OnFragmentInteractionListener {

    }

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ArrayList<Forecast> arrayList = new ArrayList<>();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        listView = view.findViewById(R.id.listview);
        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);
        Call<com.example.tanvir.weatherreport.models.forecast_models.Forecast> getForecast = weatherApi.getForecast();

        getForecast.enqueue(new Callback<com.example.tanvir.weatherreport.models.forecast_models.Forecast>() {
            @Override
            public void onResponse(Call<com.example.tanvir.weatherreport.models.forecast_models.Forecast> call, Response<com.example.tanvir.weatherreport.models.forecast_models.Forecast> response) {

                com.example.tanvir.weatherreport.models.forecast_models.Forecast forecast = response.body();
                for(int i=0;i<7;){

                    String iconUrl = "http://openweathermap.org/img/w/" + forecast.getList().get(i).getWeather().get(0).getIcon()+ ".png";
                    String dateFormate = dateFormate(forecast.getList().get(i).getDt());

                    String dayOfWeek = dayOfWeek(forecast.getList().get(i).getDt());

                    double minTemp = forecast.getList().get(i).getTemp().getMin();
                    double maxTemp = forecast.getList().get(i).getTemp().getMax();

                    arrayList.add(new Forecast(dayOfWeek,
                                "Min: "+new DecimalFormat("#.#").format(minTemp)+" "+"\u2103",
                                    "Max: "+new DecimalFormat("#.#").format(maxTemp)+" "+"\u2103",
                                dateFormate,iconUrl
                                ));
                        i++;
                }
                arrayAdapter = new ForecastAdapter(getActivity(),arrayList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<com.example.tanvir.weatherreport.models.forecast_models.Forecast> call, Throwable t) {

            }
        });


        return view;
    }

    public String dayOfWeek(int dt){
        Date date = new java.util.Date(dt*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    //date formation
    private String dateFormate(int dt) {
        Date date = new java.util.Date(dt*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM dd, yyyy");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
