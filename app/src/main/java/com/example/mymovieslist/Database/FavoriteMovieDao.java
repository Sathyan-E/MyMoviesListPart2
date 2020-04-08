package com.example.mymovieslist.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mymovieslist.Movie;

import java.util.List;
@Dao
public interface FavoriteMovieDao {
//method for loading the data from the Database
    @Query("SELECT * FROM Favorites")
    LiveData<List<Movie>> loadAllFavoriteMovies();
//method for  inserting data into the Database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavMovie(Movie movie);
//method for updating the data
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavMovie(Movie movie);
//method for deleting the data
    @Delete
    void deleteFavMovie(Movie movie);
   @Query("SELECT id FROM Favorites")
    List<Integer> loadFavMoviesID();
}
