package com.example.mymovieslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovieslist.Database.FavoriteMovieDao;
import com.example.mymovieslist.Database.FavoriteMovieDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import static com.example.mymovieslist.MyHelper.requestresponse;
import static com.example.mymovieslist.MyHelper.trailerParseResponse;
import static com.example.mymovieslist.MyHelper.userReviewResponseParsing;

public class DetailsActivity extends AppCompatActivity  {
    //global view variables
    TextView title,overview,vote,date;
    ImageView posterImage,favoriteImageview;

    ListView trailerListView,reviewListView;
    TrailerAdapter trailerAdapter;
    UserReviewAdapter reviewAdapter;
    private final String MY_API_KEY="9911d97e93c99a940a3fa35872d48420";
    private  int movieID;

    boolean isFavorite;

    String movieTitle,movieSynopsis,imagePath,movieDate;
    double movieRating;

    Movie current;
    //database instance variable
    private FavoriteMovieDatabase myDatabase;
    private FavoriteMovieDao myFavDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //binding methods
        title=(TextView)findViewById(R.id.title_value);
        overview=(TextView)findViewById(R.id.overview_value);
        vote=(TextView)findViewById(R.id.rating_value);
        date=(TextView)findViewById(R.id.releasedate_value);
        posterImage=(ImageView)findViewById(R.id.posterimage);

        //inializing database
        myDatabase=FavoriteMovieDatabase.getInstance(getApplicationContext());


        favoriteImageview = (ImageView)findViewById(R.id.favorite_imageview);

        //retrieving datas from intent
        movieTitle=getIntent().getExtras().getString("title");
        String movieSynopsis=getIntent().getExtras().getString("overview");
        String movieRating =getIntent().getExtras().getString("rating");
        String movieDate = getIntent().getExtras().getString("release");
        String imagePath=getIntent().getExtras().getString("image");
        movieID=getIntent().getExtras().getInt("id");
        isFavorite=getIntent().getExtras().getBoolean("isfavorite");
        Log.i("parcelable id checking","At DetailsActovoty"+movieID);

        //AppExecutor for gettinng info from the databae
        AppExecutors.getInstance().getDiskIo().execute(new Runnable() {
            @Override
            public void run() {
                List<Integer> idList =myDatabase.myFavoriteMovieDao().loadFavMoviesID();

                for (int i=0;i<idList.size();i++)
                {
                    if (movieID == idList.get(i)){
                        isFavorite=true;
                        break;
                    }
                }
                if (isFavorite==true)
                {
                    favoriteImageview.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                }
                else
                {
                    favoriteImageview.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                }

            }
        });

        //setting data into views.
        title.setText(movieTitle);
        overview.setText(movieSynopsis);
        vote.setText(movieRating+"/10");
        date.setText(movieDate);

        //base url for getting image
        String baseUrl= "http://image.tmdb.org/t/p/w185";
        String urlstring =baseUrl+imagePath;
        Log.i("url checking","at bindview"+urlstring);

        //setting image through Picasso library on Imageview
        Picasso.get().load(urlstring).into(posterImage);

        //list view binding
        trailerListView=(ListView)findViewById(R.id.trailer_listview);
        trailerListView.setAdapter(trailerAdapter);
        //setting onCLickListener to trailerview
        trailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Trailer currentTrailer = trailerAdapter.getItem(position);
                if (currentTrailer!=null)
                {
                    String key=currentTrailer.getKey();
                    Toast.makeText(DetailsActivity.this, "Item clicked"+key, Toast.LENGTH_SHORT).show();
                    String weblink="https://wwww.youtube.com/watch?v="+key;
                    Log.i("youtube checking","At intent"+weblink);
                    //implicit Intent to play the video in youtube
                    Intent goYouTube = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v="+key));
                    try {
                        DetailsActivity.this.startActivity(goYouTube);
                    } catch (ActivityNotFoundException e) {
                        Log.i("Error on","YouTube");
                        e.printStackTrace();
                    }

                }
            }
        });
        //user review List View binding
        reviewListView=(ListView)findViewById(R.id.review_listview);
        reviewListView.setAdapter(reviewAdapter);
        //setting onclickListener foor reviewlistview
        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserReview currentReview=reviewAdapter.getItem(position);
                if (currentReview!=null)
                {
                    String reviewUrl=currentReview.getUrl();
                    Toast.makeText(DetailsActivity.this, "review clicked", Toast.LENGTH_SHORT).show();
                    //implicit intent for open teh review in browser
                    Intent goReview= new Intent(Intent.ACTION_VIEW,Uri.parse(reviewUrl));
                    try {
                        DetailsActivity.this.startActivity(goReview);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        String  TRAILER_LINK ="https://api.themoviedb.org/3/movie/"+movieID+"/videos?api_key="+MY_API_KEY;
        String REVIEW_LINK ="https://api.themoviedb.org/3/movie/"+movieID+"/reviews?api_key="+MY_API_KEY;
        //initializing async tasks
        new TrailerAsycTask().execute(TRAILER_LINK);
        new UserReviewAsyncTask().execute(REVIEW_LINK);
    }

    public void favoriteClicked(View view) {
        final Movie favMovie=new Movie(movieID,movieTitle);

        if (isFavorite==true)
        {
            favoriteImageview.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            Toast.makeText(this, "Removed from  Favorites", Toast.LENGTH_SHORT).show();
            AppExecutors.getInstance().getDiskIo().execute(new Runnable() {
                @Override
                public void run() {
                    myDatabase.myFavoriteMovieDao().deleteFavMovie(favMovie);
                }
            });
            isFavorite=false;
        }
        else {
            favoriteImageview.setColorFilter(Color.RED,PorterDuff.Mode.SRC_IN);
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            AppExecutors.getInstance().getDiskIo().execute(new Runnable() {
                @Override
                public void run() {
                    myDatabase.myFavoriteMovieDao().insertFavMovie(favMovie);
                }
            });
            isFavorite=true;
        }
    }

    public class TrailerAsycTask extends AsyncTask<String,Void, List<Trailer>>
    {

        @Override
        protected List<Trailer> doInBackground(String... strings) {

            String jSOnResponse="";
            String link=strings[0];
            //parsing the JSON data got form the api
            jSOnResponse=requestresponse(link);
            Log.i("REsponse CHeck","At details Activity"+jSOnResponse);
            return trailerParseResponse(jSOnResponse);
        }

        @Override
        protected void onPostExecute(List<Trailer> trailerList) {
            super.onPostExecute(trailerList);
            Log.i("tlist size checking","At OnPostExcute method"+trailerList.size());
           //initialization of tralier Adapter
            trailerAdapter= new TrailerAdapter(DetailsActivity.this,trailerList);
            trailerListView.setAdapter(trailerAdapter);
        }
    }

    public class UserReviewAsyncTask extends AsyncTask<String,Void,List<UserReview>>
    {

        @Override
        protected List<UserReview> doInBackground(String... strings) {
            String jsonResponse="";
            String reviewurl=strings[0];
            //parsing the data got from the api
            jsonResponse=requestresponse(reviewurl);
            return userReviewResponseParsing(jsonResponse);
        }

        @Override
        protected void onPostExecute(List<UserReview> userReviews) {
            super.onPostExecute(userReviews);
            //initialization of adapter
            reviewAdapter=new UserReviewAdapter(DetailsActivity.this,userReviews);
            reviewListView.setAdapter(reviewAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
