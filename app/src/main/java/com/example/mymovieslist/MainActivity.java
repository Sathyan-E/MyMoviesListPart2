package com.example.mymovieslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.mymovieslist.CustomAdapter.MyViewHolder;
import java.util.List;

import static com.example.mymovieslist.MyHelper.requestresponse;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnItemListener {

    private static String MY_API="9911d97e93c99a940a3fa35872d48420";
    private static String FEED_URL_POPULARMOVIES= "https://api.themoviedb.org/3/movie/popular?api_key="+MY_API+"&language=en-US&page=1";
    private static String FEED_URL_TOPRATED= "https://api.themoviedb.org/3/movie/top_rated?api_key="+MY_API+"&language=en-US&page=1";
    private static  List<Movie> myList;
    private CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    public CustomAdapter.OnItemListener onItemListener;

   Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //get the reference of recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //set the grid layout manager with default veritcal orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        ItemDecorator itemDecorator= new ItemDecorator(getApplicationContext(),R.dimen.offset);
        recyclerView.addItemDecoration(itemDecorator);
        //GridView gridView = (GridView)findViewById(R.id.mygrid);
        MyASynTask myASynTask= new MyASynTask();
        myASynTask.execute(FEED_URL_TOPRATED);


        //call the constructor of the customAdapter to send the reference and data to Adapter

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
                startActivity(goDetails);
            }
        };

    }

            @Override
            public void onMovieClick(int position) {

            }

            public  class MyASynTask extends AsyncTask<String,Void, List<Movie>>  {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String link = strings[0];
           List<Movie> li= requestresponse(link);
            //
            return li;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
             myList=movies;
            Log.i("list length check","at asynctask  class"+myList.size());
            customAdapter = new CustomAdapter(MainActivity.this,myList,onItemListener);
            recyclerView.setAdapter(customAdapter);
        }
    }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {

                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.settings,menu);
                return true;
            }

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


            return true;
            }
        }
