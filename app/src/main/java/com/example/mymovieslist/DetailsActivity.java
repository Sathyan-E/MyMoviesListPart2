package com.example.mymovieslist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView title,overview,vote,date;
    ImageView posterImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title=(TextView)findViewById(R.id.title_value);
        overview=(TextView)findViewById(R.id.overview_value);
        vote=(TextView)findViewById(R.id.rating_value);
        date=(TextView)findViewById(R.id.releasedate_value);
        posterImage=(ImageView)findViewById(R.id.posterimage);


        String movieTitle=getIntent().getExtras().getString("title");
        String movieSynopsis=getIntent().getExtras().getString("overview");
        String movieRating =getIntent().getExtras().getString("rating");
        String movieDate = getIntent().getExtras().getString("release");
        String imagePath=getIntent().getExtras().getString("image");
        title.setText(movieTitle);
        overview.setText(movieSynopsis);
        vote.setText(movieRating);
        date.setText(movieDate);
        String baseUrl= "http://image.tmdb.org/t/p/w185";
        String urlstring =baseUrl+imagePath;
        Log.i("url checking","at bindview"+urlstring);
        Picasso.get().load(urlstring).into(posterImage);
    }
}
