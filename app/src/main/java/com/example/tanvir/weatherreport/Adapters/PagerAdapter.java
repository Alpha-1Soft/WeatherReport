package com.example.tanvir.weatherreport.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tanvir.weatherreport.fragments.CurrectWeatherFragment;
import com.example.tanvir.weatherreport.fragments.WeatherForecastFragment;

public class PagerAdapter extends FragmentStatePagerAdapter{
    int totalTab;
    public PagerAdapter(FragmentManager fm, int totalTab) {
        super(fm);
        this.totalTab = totalTab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                CurrectWeatherFragment currectWeatherFragment = new CurrectWeatherFragment();
                return currectWeatherFragment;
            case 1:
                WeatherForecastFragment weatherForecastFragment = new WeatherForecastFragment();
                return weatherForecastFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
