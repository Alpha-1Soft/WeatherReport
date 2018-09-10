package com.example.tanvir.weatherreport.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tanvir.weatherreport.R;
import com.example.tanvir.weatherreport.fragments.CurrectWeatherFragment;
import com.example.tanvir.weatherreport.fragments.WeatherForecastFragment;

public class MainActivity extends AppCompatActivity implements CurrectWeatherFragment.OnFragmentInteractionListener,WeatherForecastFragment.OnFragmentInteractionListener {
    TabLayout tabLayout;
    SharedPreferences myPrefs;
    ViewPager viewPager;
    int clickCount = 0,clickCount1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tablayoutId);
        tabLayout.addTab(tabLayout.newTab().setText("Current"));
        tabLayout.addTab(tabLayout.newTab().setText("7 days forecast"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.viewpagerId);
        PagerAdapter pagerAdapter = new com.example.tanvir.weatherreport.Adapters.
                PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        //finding search
        MenuItem search = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) search.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                PagerAdapter pagerAdapter = (PagerAdapter) viewPager.getAdapter();

                //for (int i = 0; i < pagerAdapter.getCount(); i++) {

                    Fragment viewPagerFragment = (Fragment) viewPager
                            .getAdapter().instantiateItem(viewPager, 1);
                Fragment viewPagerFragment1 = (Fragment) viewPager
                        .getAdapter().instantiateItem(viewPager, 0);

                if (viewPagerFragment != null
                            && viewPagerFragment.isAdded()) {

                        if (viewPagerFragment instanceof WeatherForecastFragment || viewPagerFragment1 instanceof  CurrectWeatherFragment) {
                            final WeatherForecastFragment weatherForecastFragment = (WeatherForecastFragment) viewPagerFragment;
                            CurrectWeatherFragment currectWeatherFragment = (CurrectWeatherFragment) viewPagerFragment1;
                            if (weatherForecastFragment != null && currectWeatherFragment!=null) {
                                myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
                                String key = myPrefs.getString("Key", null);
                                currectWeatherFragment.getWeatherDataBySearch(searchText,key);
                                weatherForecastFragment.beginSearch(searchText, key);

                                myPrefs = getSharedPreferences("queryId", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = myPrefs.edit();
                                editor1.putString("location", searchText);
                                editor1.apply();
                                editor1.commit();
                            }
                        }
                    //}
                }
                return false;
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
        int id = item.getItemId();
        switch (id) {
            case R.id.degreeCelcius:

                Toast.makeText(this, "Celsius mode enabled ", Toast.LENGTH_SHORT).show();
                //getting search text from sharedpreferences
                myPrefs = getSharedPreferences("queryId", Context.MODE_PRIVATE);
                String query = myPrefs.getString("location", null);

                //key value storing on sharedpreferences
                myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("Key", "1");
                editor.apply();
                editor.commit();

                //getting key value
                myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
                String key = myPrefs.getString("Key", null);


                PagerAdapter pagerAdapter = (PagerAdapter) viewPager.getAdapter();

                //calling fragmrnt
                Fragment viewPagerFragment = (Fragment) viewPager
                        .getAdapter().instantiateItem(viewPager, 1);
                Fragment viewPagerFragment1 = (Fragment) viewPager
                        .getAdapter().instantiateItem(viewPager, 0);

                if (viewPagerFragment != null
                        && viewPagerFragment.isAdded()) {

                    if (viewPagerFragment instanceof WeatherForecastFragment || viewPagerFragment1 instanceof  CurrectWeatherFragment) {
                        final WeatherForecastFragment weatherForecastFragment = (WeatherForecastFragment) viewPagerFragment;
                        CurrectWeatherFragment currectWeatherFragment = (CurrectWeatherFragment) viewPagerFragment1;
                        if (weatherForecastFragment != null && currectWeatherFragment!=null) {
                            if (query==null){//checking mode click if default then show dhaka,bd's temp
                                weatherForecastFragment.beginSearch("Dhaka,bd",key);
                                currectWeatherFragment.getWeatherDataBySearch("Dhaka,bd",key);

                            }
                            else if(query.length()>0){//else user search any query then it works with it
                                weatherForecastFragment.beginSearch(query,key);
                                currectWeatherFragment.getWeatherDataBySearch(query,key);
                            }
                        }
                    }

                }
                clickCount++;
                break;

            case R.id.degreeFarenhite:
                //getting search text from sharedpreferences
                myPrefs = getSharedPreferences("queryId", Context.MODE_PRIVATE);
                String query1 = myPrefs.getString("location", null);

                Toast.makeText(this, "Fahrenheit mode enabled", Toast.LENGTH_SHORT).show();

                //key value storing on sharedpreferences
                myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = myPrefs.edit();
                editor1.putString("Key", "2");
                editor1.apply();
                editor1.commit();

                //getting key value
                myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
                String key1 = myPrefs.getString("Key", null);

                PagerAdapter pagerAdapter1 = (PagerAdapter) viewPager.getAdapter();

                Fragment viewPagerFragment2 = (Fragment) viewPager
                        .getAdapter().instantiateItem(viewPager, 1);
                Fragment viewPagerFragment3 = (Fragment) viewPager
                        .getAdapter().instantiateItem(viewPager, 0);

                if (viewPagerFragment2 != null
                        && viewPagerFragment2.isAdded()) {

                    if (viewPagerFragment2 instanceof WeatherForecastFragment || viewPagerFragment3 instanceof  CurrectWeatherFragment) {
                        final WeatherForecastFragment weatherForecastFragment = (WeatherForecastFragment) viewPagerFragment2;
                        CurrectWeatherFragment currectWeatherFragment = (CurrectWeatherFragment) viewPagerFragment3;
                        if (weatherForecastFragment != null && currectWeatherFragment!=null) {
                            if (query1==null){//checking mode click if default then show dhaka,bd's temp
                                weatherForecastFragment.beginSearch("Dhaka,bd",key1);
                                currectWeatherFragment.getWeatherDataBySearch("Dhaka,bd",key1);
                            }
                            else if(query1.length()>0){//else user search any query then it works with it
                                weatherForecastFragment.beginSearch(query1,key1);
                                currectWeatherFragment.getWeatherDataBySearch(query1,key1);
                            }
                        }
                    }

                }
                clickCount1++;
                break;

            default:
                break;
        }
        return true;
    }
    //reseting search text when onstart method called
    @Override
    protected void onStart() {
        super.onStart();
        final SharedPreferences pref = getSharedPreferences("queryId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().commit();
    }
}
