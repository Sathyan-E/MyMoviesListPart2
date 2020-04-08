package com.example.mymovieslist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends ArrayAdapter<Trailer> {

    List<Trailer> myTrailerList = new ArrayList<Trailer>();
    Activity context;
    //Constructor
    TrailerAdapter(Activity context, List<Trailer> trailerList)
    {
        super(context,0,trailerList);
        myTrailerList=trailerList;
        this.context=context;
    }
    //method to get views
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null)
        {
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.trailer_list_item,parent,false);
        }
        Trailer currentTrailer = getItem(position);

        TextView trailerName = (TextView)convertView.findViewById(R.id.trailer_name);

        trailerName.setText(currentTrailer.getName());

        return convertView;

    }
}
