package com.example.mymovieslist;

public class Movie {
   //attributes
    private String movieTitle;
    private String synopsis;
    private double userRating;
    private  String releaseDate;
    private String imageUrl;
    private int id;
    //constructor
    public Movie()
    {    }
    //constructor
    public Movie(int id,String title,String image,String overview,double rating,String release)
    {
        movieTitle=title;
        imageUrl = image;
        synopsis=overview;
        userRating=rating;
        releaseDate=release;
        this.id=id;
    }
    //getter methods for all the attributes.

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getId() {
        return id;
    }
}
