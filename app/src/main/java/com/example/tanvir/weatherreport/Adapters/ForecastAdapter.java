package com.example.tanvir.weatherreport.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanvir.weatherreport.R;
import com.example.tanvir.weatherreport.models.needy_models.Forecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ForecastAdapter extends ArrayAdapter<Forecast> {
    ArrayList<Forecast> forecastArrayList;
    Context context;

  public ForecastAdapter(Context context,ArrayList<Forecast> forecastArrayList){
      super(context,R.layout.listview_shape,forecastArrayList);
      this.context = context;
      this.forecastArrayList=forecastArrayList;
  }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){//if view null then create new view
            view= LayoutInflater.from(context).inflate(R.layout.listview_shape,parent,false);//for creating view
        }

        Forecast item = forecastArrayList.get(position);

        //finding listview shape component
        TextView dayTv = view.findViewById(R.id.dayTv);
        TextView dateTv = view.findViewById(R.id.dateTv);
        TextView minTempTv = view.findViewById(R.id.minTempTv);
        TextView maxTempTv = view.findViewById(R.id.maxTempTv);
        ImageView imageView = view.findViewById(R.id.weatherConditionIm);
        //return super.getView(position, convertView, parent);



        //setting listview shape component to arrryList
        dayTv.setText(item.getDay());
        dateTv.setText(item.getDate());
        minTempTv.setText(item.getMinTemp());
        maxTempTv.setText(item.getMaxTemp());
        Picasso.with(context).load(item.getImage()).into(imageView);//calling picsasso library for image loading

        return view;
    }
}
