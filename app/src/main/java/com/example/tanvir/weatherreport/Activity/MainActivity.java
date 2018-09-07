package com.example.tanvir.weatherreport.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.tanvir.weatherreport.R;
import com.example.tanvir.weatherreport.RetrofitClient;
import com.example.tanvir.weatherreport.WeatherApi;
import com.example.tanvir.weatherreport.fragments.CurrectWeatherFragment;
import com.example.tanvir.weatherreport.fragments.WeatherForecastFragment;
import com.example.tanvir.weatherreport.models.weather_models.Weather;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CurrectWeatherFragment.OnFragmentInteractionListener,WeatherForecastFragment.OnFragmentInteractionListener{
    WeatherApi weatherApi;
    TabLayout tabLayout;
    String searchCurrentWeather=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWeather();


        tabLayout = (TabLayout)findViewById(R.id.tablayoutId);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar,menu);
        MenuItem search = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                if(tabLayout.getSelectedTabPosition()==0 && searchView.isIconified()==true){
                  CurrectWeatherFragment fragment = new CurrectWeatherFragment();
                  fragment.OnQueryTextListener(searchText);
                }
                return false;
            }

            public String sendData() {
                return searchCurrentWeather;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {

                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                //
                break;
                default:
                    break;
        }
        return true;
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
