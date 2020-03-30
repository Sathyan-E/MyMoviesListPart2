package com.example.mymovieslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import static com.example.mymovieslist.MyHelper.requestresponse;

public class MainActivity extends AppCompatActivity {


    private static String MY_API="9911d97e93c99a940a3fa35872d48420";
    private static String FEED_URL= "https://api.themoviedb.org/3/movie/top_rated?api_key="+MY_API+"&language=en-US&page=1";
    List<Movie> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView)findViewById(R.id.mygrid);
        MyASynTask myASynTask= new MyASynTask();
        myASynTask.execute(FEED_URL);

        final MovieAdapter movieAdapter= new MovieAdapter(MainActivity.this,myList);
        Log.i("list length checking","after movieadapter initialization"+myList.size());
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goDetails= new Intent(MainActivity.this,DetailsActivity.class);
                Movie currentMovie = movieAdapter.getItem(position);
                assert currentMovie != null;
                goDetails.putExtra("title",currentMovie.getMovieTitle());
                goDetails.putExtra("overview",currentMovie.getSynopsis());
                goDetails.putExtra("rating",String.valueOf(currentMovie.getUserRating()));
                goDetails.putExtra("releasedate",currentMovie.getReleaseDate());
                goDetails.putExtra("imageurl",currentMovie.getImageUrl());

                startActivity(goDetails);

            }
        });

    }
    public class MyASynTask extends AsyncTask<String,Void, List<Movie>>{

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String link = strings[0];
            myList =requestresponse(link);
            Log.i("list length check","at asynctask  class"+myList.size());
            return myList;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {

        }
    }
}
