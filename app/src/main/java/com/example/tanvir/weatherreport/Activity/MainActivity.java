package com.example.tanvir.weatherreport.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.tanvir.weatherreport.RetrofitClient;
import com.example.tanvir.weatherreport.WeatherApi;
import com.example.tanvir.weatherreport.fragments.CurrectWeatherFragment;
import com.example.tanvir.weatherreport.fragments.WeatherForecastFragment;
import com.example.tanvir.weatherreport.models.weather_models.Weather1;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements CurrectWeatherFragment.OnFragmentInteractionListener,WeatherForecastFragment.OnFragmentInteractionListener {
    TabLayout tabLayout;
    SharedPreferences myPrefs;
    ViewPager viewPager;

    WeatherApi weatherApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isConnectNetwork()){
            Toast.makeText(this, "Network connection established", LENGTH_SHORT).show();
        }
        else{
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("No internet connection");
            builder.setMessage("Please check your internet connection and try again");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            builder.show();
        }

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
                checkCity(searchText);
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

                //getting search text from sharedpreferences
                myPrefs = getSharedPreferences("queryId", Context.MODE_PRIVATE);
                String query = myPrefs.getString("location", null);

                Toast.makeText(this, "Celsius mode enabled "+query, LENGTH_SHORT).show();

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
                break;

            case R.id.degreeFarenhite:
                //getting search text from sharedpreferences
                myPrefs = getSharedPreferences("queryId", Context.MODE_PRIVATE);
                String query1 = myPrefs.getString("location", null);

                Toast.makeText(this, "Fahrenheit mode enabled", LENGTH_SHORT).show();

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
                break;

            default:
                break;
        }
        return true;
    }
    //network connection check
    public boolean isConnectNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        else{
            return false;
        }
    }
    //reseting search text when onstart method called
    @Override
    protected void onStart() {
        super.onStart();
        final SharedPreferences pref = getSharedPreferences("queryId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().commit();
    }

    //checking city name here
    public void checkCity(final String query){


        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);

        String url = "data/2.5/weather?q="+query+"&apikey=90ff8755cfe4bfaa6e542e82cafe5b3e";

        Call<Weather1> weather1Call = weatherApi.getWeatherBySearch(url);

        weather1Call.enqueue(new Callback<Weather1>() {
            @Override
            public void onResponse(Call<Weather1> call, Response<Weather1> response) {
                if(response.isSuccessful() && response.body()!=null){
                    PagerAdapter pagerAdapter = (PagerAdapter) viewPager.getAdapter();

                    Fragment viewPagerFragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, 1);
                    Fragment viewPagerFragment1 = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);

                    if (viewPagerFragment != null && viewPagerFragment.isAdded()) {

                        if (viewPagerFragment instanceof WeatherForecastFragment || viewPagerFragment1 instanceof  CurrectWeatherFragment) {
                            final WeatherForecastFragment weatherForecastFragment = (WeatherForecastFragment) viewPagerFragment;
                            CurrectWeatherFragment currectWeatherFragment = (CurrectWeatherFragment) viewPagerFragment1;
                            if (weatherForecastFragment != null && currectWeatherFragment!=null) {
                                myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
                                String key = myPrefs.getString("Key", "1");

                                    weatherForecastFragment.beginSearch(query,key);
                                    currectWeatherFragment.getWeatherDataBySearch(query,key);

                                    myPrefs = getSharedPreferences("queryId", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor1 = myPrefs.edit();
                                    editor1.putString("location", query);
                                    editor1.apply();
                                    editor1.commit();
                                }
                            }
                        }

                }
                else{
                    Toast.makeText(MainActivity.this, "Invalid city name", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Weather1> call, Throwable t) {

            }
        });

    }
}
