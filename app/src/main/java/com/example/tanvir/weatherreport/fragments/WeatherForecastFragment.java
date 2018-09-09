package com.example.tanvir.weatherreport.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanvir.weatherreport.Adapters.ForecastAdapter;
import com.example.tanvir.weatherreport.R;
import com.example.tanvir.weatherreport.RetrofitClient;
import com.example.tanvir.weatherreport.WeatherApi;
import com.example.tanvir.weatherreport.models.needy_models.Forecast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherForecastFragment extends Fragment {
    WeatherApi weatherApi;
    ListView listView;
    ArrayAdapter arrayAdapter;
    SharedPreferences myPrefs;
    final ArrayList<Forecast> arrayList = new ArrayList<>();

    public interface OnFragmentInteractionListener {

    }

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        listView = view.findViewById(R.id.listview);
        myPrefs = getActivity().getSharedPreferences("prefID", Context.MODE_PRIVATE);
        String key = myPrefs.getString("Key",null);
        getForecastData(key);

        return view;
    }

    //fetching default forecast
    public void getForecastData(final String key) {
        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);
        Call<com.example.tanvir.weatherreport.models.forecast_models.Forecast> getForecast = weatherApi.getForecast();

        getForecast.enqueue(new Callback<com.example.tanvir.weatherreport.models.forecast_models.Forecast>() {
            @Override
            public void onResponse(Call<com.example.tanvir.weatherreport.models.forecast_models.Forecast> call, Response<com.example.tanvir.weatherreport.models.forecast_models.Forecast> response) {

                com.example.tanvir.weatherreport.models.forecast_models.Forecast forecast = response.body();
                for (int i = 0; i < 7; ) {
                    //icon url
                    String iconUrl = "http://openweathermap.org/img/w/" + forecast.getList().get(i).getWeather().get(0).getIcon() + ".png";
                    //date formation
                    String dateFormate = dateFormate(forecast.getList().get(i).getDt());

                    //day of week formation
                    String dayOfWeek = dayOfWeek(forecast.getList().get(i).getDt());

                    double minTemp = forecast.getList().get(i).getTemp().getMin();
                    double maxTemp = forecast.getList().get(i).getTemp().getMax();

                        if(key.equals("1") || key.equals(null)){//if mode is 1 then insert celsius data
                            if(i==0){
                                arrayList.add(new Forecast("Today",
                                        "Min: " + new DecimalFormat("#.#").format(minTemp) + " " + "\u2103",
                                        "Max: " + new DecimalFormat("#.#").format(maxTemp) + " " + "\u2103",
                                        dateFormate, iconUrl
                                ));
                            }
                            else if(i==1){
                                arrayList.add(new Forecast(dayOfWeek,
                                        "Min: " + new DecimalFormat("#.#").format(minTemp) + " " + "\u2103",
                                        "Max: " + new DecimalFormat("#.#").format(maxTemp) + " " + "\u2103",
                                        dateFormate, iconUrl
                                ));
                            }
                            else{
                                arrayList.add(new Forecast(dayOfWeek,
                                        "Min: " + new DecimalFormat("#.#").format(minTemp) + " " + "\u2103",
                                        "Max: " + new DecimalFormat("#.#").format(maxTemp) + " " + "\u2103",
                                        dateFormate, iconUrl
                                ));
                            }

                        }
                        else if(key.equals("2")){//else if mode is 2 then insert farenhite
                            if(i==0){
                                arrayList.add(new Forecast("Today",
                                        "Min: " + celsiusToFarenhite(minTemp),
                                        "Max: " + celsiusToFarenhite(maxTemp),
                                        dateFormate, iconUrl
                                ));
                            }
                            else if(i==1){
                                arrayList.add(new Forecast("Tomorrow",
                                        "Min: " + celsiusToFarenhite(minTemp),
                                        "Max: " + celsiusToFarenhite(maxTemp),
                                        dateFormate, iconUrl
                                ));
                            }
                            else{
                                arrayList.add(new Forecast(dayOfWeek,
                                        "Min: " + celsiusToFarenhite(minTemp),
                                        "Max: " + celsiusToFarenhite(maxTemp),
                                        dateFormate, iconUrl
                                ));
                            }
                        }
                    i++;
                }
                arrayAdapter = new ForecastAdapter(getActivity(), arrayList);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<com.example.tanvir.weatherreport.models.forecast_models.Forecast> call, Throwable t) {

            }
        });
    }

    //fetching forecast weather by search
    public void beginSearch(String location, final String key) {
        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);

        String foreCastUrl = "data/2.5/forecast/daily?q=" + location + "&units=metric&cnt=7&appid=c0c4a4b4047b97ebc5948ac9c48c0559";
        Call<com.example.tanvir.weatherreport.models.forecast_models.Forecast> forecastCallBySearch = weatherApi.getForecastBySearch(foreCastUrl);

        forecastCallBySearch.enqueue(new Callback<com.example.tanvir.weatherreport.models.forecast_models.Forecast>() {
            @Override
            public void onResponse(Call<com.example.tanvir.weatherreport.models.forecast_models.Forecast> call, Response<com.example.tanvir.weatherreport.models.forecast_models.Forecast> response) {
                if (response.isSuccessful() && response.body() != null) {
                    com.example.tanvir.weatherreport.models.forecast_models.Forecast forecastBySearch = response.body();

                    for (int i = 0; i < 7; ) {

                        //icon url
                        String iconUrl = "http://openweathermap.org/img/w/" + forecastBySearch.getList().get(i).getWeather().get(0).getIcon() + ".png";
                        //date formation
                        String dateFormate = dateFormate(forecastBySearch.getList().get(i).getDt());

                        //day of week formation
                        String dayOfWeek = dayOfWeek(forecastBySearch.getList().get(i).getDt());

                        double minTemp = forecastBySearch.getList().get(i).getTemp().getMin();
                        double maxTemp = forecastBySearch.getList().get(i).getTemp().getMax();

                            if (key.equals("1") || key.equals(null)) {//if mode is 1 then insert celsius data
                                if(i==0){
                                    arrayList.set(i, new Forecast("Today",
                                            "Min: " + new DecimalFormat("#.#").format(minTemp) + " " + "\u2103",
                                            "Max: " + new DecimalFormat("#.#").format(maxTemp) + " " + "\u2103",
                                            dateFormate, iconUrl)
                                    );
                                }
                                else if(i==1){
                                    arrayList.set(i, new Forecast("Tomorrow",
                                            "Min: " + new DecimalFormat("#.#").format(minTemp) + " " + "\u2103",
                                            "Max: " + new DecimalFormat("#.#").format(maxTemp) + " " + "\u2103",
                                            dateFormate, iconUrl)
                                    );
                                }
                                else{
                                    arrayList.set(i, new Forecast(dayOfWeek,
                                            "Min: " + new DecimalFormat("#.#").format(minTemp) + " " + "\u2103",
                                            "Max: " + new DecimalFormat("#.#").format(maxTemp) + " " + "\u2103",
                                            dateFormate, iconUrl)
                                    );
                                }
                            } else if (key.equals("2")) {//else if mode is 2 then insert farenhite
                                if(i==0){
                                    arrayList.set(i, new Forecast("Today",
                                            "Min: " + celsiusToFarenhite(minTemp) ,
                                            "Max: " + celsiusToFarenhite(maxTemp),
                                            dateFormate, iconUrl)
                                    );
                                }
                                else if(i==1){
                                    arrayList.set(i, new Forecast("Tomorrow",
                                            "Min: " + celsiusToFarenhite(minTemp) ,
                                            "Max: " + celsiusToFarenhite(maxTemp),
                                            dateFormate, iconUrl)
                                    );
                                }
                                else{
                                    arrayList.set(i, new Forecast(dayOfWeek,
                                            "Min: " + celsiusToFarenhite(minTemp) ,
                                            "Max: " + celsiusToFarenhite(maxTemp),
                                            dateFormate, iconUrl)
                                    );
                                }
                        }
                        i++;
                    }
                    arrayAdapter = new ForecastAdapter(getActivity(), arrayList);
                    listView.setAdapter(arrayAdapter);
                } else {
                    Toast.makeText(getActivity(), "Invalid city name", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.tanvir.weatherreport.models.forecast_models.Forecast> call, Throwable t) {

            }
        });

    }

    //day formation from unix value
    public String dayOfWeek(int dt) {
        Date date = new java.util.Date(dt * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+6"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    //date formation from unix value
    private String dateFormate(int dt) {
        Date date = new java.util.Date(dt * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM dd, yyyy");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+6"));//setting time zone
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


    public String celsiusToFarenhite(double c){

        Double f = ((9/5)*c) + 32;
        String farenhite = new DecimalFormat("#.#").format(f) + " " + "\u2109";
        return  farenhite;
    }

}
