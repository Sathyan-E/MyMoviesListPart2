package com.example.mymovieslist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "Favorites")
public class Movie implements Serializable {
   //Member Variables
   @PrimaryKey
   private int id;
    @ColumnInfo(name = "Title")
    private String movieTitle;
    @Ignore
    private String synopsis;
    @Ignore
    private double userRating;
    @Ignore
    private  String releaseDate;
    @Ignore
    private String imageUrl;

    @Ignore
    private boolean fav;
    //constructor

    //constructor
    @Ignore
    public Movie(int id,String movieTitle,String imageUrl,String synopisis,double userRating,String releaseDate,boolean fav)
    {
        this.movieTitle=movieTitle;
       this.imageUrl = imageUrl;
       this.synopsis=synopisis;
       this.userRating=userRating;
       this.releaseDate=releaseDate;
        this.id=id;
        this.fav=fav;
    }

    public Movie( int id, String movieTitle){
        this.movieTitle=movieTitle;
        this.id=id;
    }
    //Setter and Getter methods for all member viariables
    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

}
