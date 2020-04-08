package com.example.mymovieslist.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mymovieslist.Movie;


    @Database(entities = {Movie.class},version = 1,exportSchema = false)
    public abstract class FavoriteMovieDatabase extends RoomDatabase {
        private static final String LOG_TAG=FavoriteMovieDatabase.class.getSimpleName();
        private static final Object LOCK = new Object();
        private static final String DATABASE_NAME="MoviesList";
        private static FavoriteMovieDatabase sInstance;

        public static FavoriteMovieDatabase getInstance(Context context)
        {
            if (sInstance == null)
            {
                synchronized (LOCK)
                {
                    Log.d(LOG_TAG,"Creating new Database Insatance");
                    sInstance= Room.databaseBuilder(context.getApplicationContext(),FavoriteMovieDatabase.class,
                            FavoriteMovieDatabase.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }

            Log.d(LOG_TAG,"Getting the database instance");
            return sInstance;
        }
        //Deviation - removed absratc key word due to error
        public abstract FavoriteMovieDao myFavoriteMovieDao();
    }


