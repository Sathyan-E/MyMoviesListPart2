package com.example.mymovieslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.List;

import static com.example.mymovieslist.MyHelper.parseResponse;
import static com.example.mymovieslist.MyHelper.requestresponse;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnItemListener {
    //global variables
    private final String MY_API_KEY="9911d97e93c99a940a3fa35872d48420";
    private  final String FEED_URL_POPULARMOVIES= "https://api.themoviedb.org/3/movie/popular?api_key="+MY_API_KEY+"&language=en-US&page=1";
    private final String FEED_URL_TOPRATED= "https://api.themoviedb.org/3/movie/top_rated?api_key="+MY_API_KEY+"&language=en-US&page=1";
    private static  List<Movie> myList;
    private RecyclerView recyclerView;
    public CustomAdapter.OnItemListener onItemListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //get the reference of recycler view
        recyclerView = findViewById(R.id.recyclerview);
        //set the grid layout manager with default veritcal orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        ItemDecorator itemDecorator= new ItemDecorator(getApplicationContext(),R.dimen.offset);
        recyclerView.addItemDecoration(itemDecorator);
        //GridView gridView = (GridView)findViewById(R.id.mygrid);
        MyASynTask myASynTask= new MyASynTask();
        myASynTask.execute(FEED_URL_TOPRATED);


        //to handle click event

        onItemListener=new CustomAdapter.OnItemListener() {
            @Override
            public void onMovieClick(int position) {
                Movie currentMovie = myList.get(position);
                Intent goDetails = new Intent(MainActivity.this,DetailsActivity.class);

                goDetails.putExtra("title",currentMovie.getMovieTitle());
                goDetails.putExtra("overview",currentMovie.getSynopsis());
                goDetails.putExtra("rating",String.valueOf(currentMovie.getUserRating()));
                Log.i("rating check","before passing"+currentMovie.getUserRating());
                goDetails.putExtra("release",currentMovie.getReleaseDate());
                goDetails.putExtra("image",currentMovie.getImageUrl());
                goDetails.putExtra("id",currentMovie.getId());
                goDetails.putExtra("isfavorite",currentMovie.isFav());
                Log.i("current id checking","at main method"+currentMovie.getId());

               //goDetails.putExtra("movieObject", currentMovie);
                startActivity(goDetails);
            }
        };

    }

    @Override
    public void onMovieClick(int position) {}
        //Async task class
    public  class MyASynTask extends AsyncTask<String,Void, List<Movie>>  {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String link = strings[0];
           //requesting api and parsing JSON response
            String response =requestresponse(link);


            return parseResponse(response);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
             myList=movies;
            CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, myList, onItemListener);
            recyclerView.setAdapter(customAdapter);
        }
    }
            //to screate options on main activity
            @Override
            public boolean onCreateOptionsMenu(Menu menu) {

                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.settings,menu);
                return true;
            }
            //to set instructions for selected options
            @Override
            public boolean onOptionsItemSelected(@NonNull MenuItem item)
            {
                int id=item.getItemId();
                if (id==R.id.top_rated)
                {
                    new MyASynTask().execute(FEED_URL_TOPRATED);
                }
                else if (id==R.id.most_popular)
                {
                    new MyASynTask().execute(FEED_URL_POPULARMOVIES);
                }
                else if (id==R.id.my_favorite)
                {
                    Intent goFavorite = new Intent(MainActivity.this, FavoriteList.class);
                    startActivity(goFavorite);
                }
                return true;
            }
}
