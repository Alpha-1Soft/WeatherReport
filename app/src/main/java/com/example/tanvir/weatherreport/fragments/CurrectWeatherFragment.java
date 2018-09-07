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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.weatherreport.Activity.MainActivity;
import com.example.tanvir.weatherreport.R;

public class CurrectWeatherFragment extends Fragment {

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

        return  view;
    }

    public void OnQueryTextListener(String text) {
        Log.d("Tanvir",""+text);
    }


}
