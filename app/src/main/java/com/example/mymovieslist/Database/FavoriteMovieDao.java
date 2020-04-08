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

    @Query("SELECT * FROM Favorites")
  LiveData<List<Movie>> loadAllFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavMovie(Movie movie);

    @Delete
    void deleteFavMovie(Movie movie);
   @Query("SELECT id FROM Favorites")
    List<Integer> loadFavMoviesID();
}
