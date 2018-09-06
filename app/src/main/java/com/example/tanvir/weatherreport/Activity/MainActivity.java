package com.example.tanvir.weatherreport.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.TextView;
import android.widget.TableLayout;

import com.example.tanvir.weatherreport.R;
import com.example.tanvir.weatherreport.RetrofitClient;
import com.example.tanvir.weatherreport.WeatherApi;
import com.example.tanvir.weatherreport.fragments.CurrectWeatherFragment;
import com.example.tanvir.weatherreport.fragments.WeatherForecastFragment;
import com.example.tanvir.weatherreport.models.weather_models.Weather;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CurrectWeatherFragment.OnFragmentInteractionListener,WeatherForecastFragment.OnFragmentInteractionListener{

    TextView textView;
    WeatherApi weatherApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWeather();


        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayoutId);
        tabLayout.addTab(tabLayout.newTab().setText("Current"));
        tabLayout.addTab(tabLayout.newTab().setText("Forecast"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewpagerId);
        PagerAdapter pagerAdapter = new com.example.tanvir.weatherreport.Adapters.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getWeather() {
        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);
        retrofit2.Call<Weather>getWeatherCall = weatherApi.getWeather();

        getWeatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(retrofit2.Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();

                Double d = (weather.getMain().getTemp()-273.15);
                //textView.setText(d.toString());
            }

            @Override
            public void onFailure(retrofit2.Call<Weather> call, Throwable t) {

            }
        });
    }

}
