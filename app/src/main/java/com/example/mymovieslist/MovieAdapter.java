package com.example.mymovieslist;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Activity context, List<Movie> movielist)
    {
        super(context,0,movielist);
        Log.i("Length checking","at Movie Adapter constructor"+movielist.size());

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.image_item,parent,false);
        }
         Movie currentMovie = getItem(position);
        ImageView imageView=(ImageView) convertView.findViewById(R.id.movieImage);
        assert currentMovie != null;
        String imageUrl=currentMovie.getImageUrl();
        Picasso.get().load("http://image.tmdb.org/t/p/w185"+imageUrl).into(imageView);
        return convertView;
    }


}
