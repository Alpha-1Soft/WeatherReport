package com.example.tanvir.weatherreport.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.weatherreport.R;
import com.example.tanvir.weatherreport.RetrofitClient;
import com.example.tanvir.weatherreport.WeatherApi;
import com.example.tanvir.weatherreport.models.weather_models.Weather1;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrectWeatherFragment extends Fragment {

    WeatherApi weatherApi;
    SharedPreferences myPrefs;

    TextView tempText, dateText, dayText,
            cityText, conditionText, minText,
            maxText, sunriseText, sunsetText,
            humidityText, pressureText;

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

        myPrefs = getActivity().getSharedPreferences("prefID", Context.MODE_PRIVATE);
        String key = myPrefs.getString("Key","1");
        getWeatherData(key);

        return  view;
    }
//getting default weather data
    private void getWeatherData(final String key) {
        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);

        final Call<Weather1> getWetherCall = weatherApi.getWeather();

        getWetherCall.enqueue(new Callback<Weather1>() {
            @Override
            public void onResponse(Call<Weather1> call, Response<Weather1> response) {
                Weather1 weather = response.body();

                String iconUrl = "http://openweathermap.org/img/w/" + weather.getWeather().get(0).getIcon()+ ".png";
                double temparature = (double)(weather.getMain().getTemp() - 273.15);
                double min = (double)(weather.getMain().getTempMin() - 273.15);
                double max = (double)(weather.getMain().getTempMax() - 273.15);

                if(key.equals("1") || key==null){//setting celsius
                    tempText.setText(new DecimalFormat("#.#").format(temparature) + " " + "\u2103");
                    minText.setText(new DecimalFormat("#.#").format(min) + " " + "\u2103");
                    maxText.setText(new DecimalFormat("#.#").format(max) + " " + "\u2103");
                }
                else if(key.equals("2")){//setting farenhite
                    tempText.setText(kelvinToFarenhite(weather.getMain().getTemp()));
                    minText.setText(kelvinToFarenhite(weather.getMain().getTempMin()));
                    maxText.setText(kelvinToFarenhite(weather.getMain().getTempMax()));
                }

                dateText.setText(dateFormate(weather.getDt()));
                dayText.setText(dayFormate(weather.getDt()));
                cityText.setText(weather.getName());
                sunriseText.setText(timeFormate(weather.getSys().getSunrise()));
                sunsetText.setText(timeFormate(weather.getSys().getSunset()));
                humidityText.setText(weather.getMain().getHumidity().toString()+" %");
                pressureText.setText(weather.getMain().getPressure().toString()+" hPa");
                conditionText.setText(weather.getWeather().get(0).getDescription());

                Picasso.with(getActivity()).load(iconUrl).into(conditionImage);//call picasso for image loading
            }

            @Override
            public void onFailure(Call<Weather1> call, Throwable t) {

            }
        });
    }
//getting data by search
    public void getWeatherDataBySearch(String location, final String key) {

        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);

        String url = "data/2.5/weather?q="+location+"&apikey=90ff8755cfe4bfaa6e542e82cafe5b3e";

        Call<Weather1> weather1Call = weatherApi.getWeatherBySearch(url);
        weather1Call.enqueue(new Callback<Weather1>() {
            @Override
            public void onResponse(Call<Weather1> call, Response<Weather1> response) {
                if(response.isSuccessful() && response.body()!=null){

                    Weather1 weather2 = response.body();

                    String iconUrl = "http://openweathermap.org/img/w/" + weather2.getWeather().get(0).getIcon()+ ".png";
                    double temparature = (double)(weather2.getMain().getTemp() - 273.15);
                    double min = (double)(weather2.getMain().getTempMin() - 273.15);
                    double max = (double)(weather2.getMain().getTempMax() - 273.15);

                    if(key.equals("1") || key==null){
                        tempText.setText(new DecimalFormat("#.#").format(temparature) + " " + "\u2103");
                        minText.setText(new DecimalFormat("#.#").format(min) + " " + "\u2103");
                        maxText.setText(new DecimalFormat("#.#").format(max) + " " + "\u2103");
                    }
                    else if(key.equals("2")){
                        tempText.setText(kelvinToFarenhite(weather2.getMain().getTemp()));
                        minText.setText(kelvinToFarenhite(weather2.getMain().getTempMin()));
                        maxText.setText(kelvinToFarenhite(weather2.getMain().getTempMax()));
                    }

                    dateText.setText(dateFormate(weather2.getDt()));
                    dayText.setText(dayFormate(weather2.getDt()));
                    cityText.setText(weather2.getName());
                    sunriseText.setText(timeFormate(weather2.getSys().getSunrise()));
                    sunsetText.setText(timeFormate(weather2.getSys().getSunset()));
                    humidityText.setText(weather2.getMain().getHumidity().toString()+"%");
                    pressureText.setText(weather2.getMain().getPressure().toString()+" hPa");
                    conditionText.setText(weather2.getWeather().get(0).getDescription());

                    Picasso.with(getActivity()).load(iconUrl).into(conditionImage);//call picasso for image loading
                }
            }

            @Override
            public void onFailure(Call<Weather1> call, Throwable t) {

            }
        });
    }


    // date format from unix value
    private String dateFormate(int dt) {
        Date date = new java.util.Date(dt*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+6"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
    // day format from unix value
    private String dayFormate(int dt) {
        Date date = new java.util.Date(dt*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+6"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
//time format from unix value
    private String timeFormate(int dt) {
        Date date = new java.util.Date(dt*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm:ss a");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+6"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String kelvinToFarenhite(double k){
       double f = (9/5*(k - 273) + 32);
        String farenhite = new DecimalFormat("#.#").format(f) + " " + "\u2109";
        return  farenhite;
    }
}
