package com.example.tanvir.weatherreport;

import com.example.tanvir.weatherreport.models.forecast_models.Forecast;
import com.example.tanvir.weatherreport.models.weather_models.Weather1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherApi {

    @GET("data/2.5/weather?q=Dhaka,bd&apikey=90ff8755cfe4bfaa6e542e82cafe5b3e")
    Call<Weather1> getWeather();

    @GET()
    Call<Weather1>getWeatherBySearch(@Url String location);


    @GET("data/2.5/forecast/daily?q=Dhaka,bd&units=metric&cnt=7&appid=c0c4a4b4047b97ebc5948ac9c48c0559")
    Call<Forecast>getForecast();

    @GET()
    Call<Forecast>getForecastBySearch(@Url String foreCast);
}
