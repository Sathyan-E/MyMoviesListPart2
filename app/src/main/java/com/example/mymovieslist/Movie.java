package com.example.mymovieslist;

public class Movie {
    private String movieTitle;
    private String synopsis;
    private double userRating;
    private  String releaseDate;
    private String imageUrl;

    public Movie()
    {    }

    public Movie(String title,String image,String overview,double rating,String release)
    {
        movieTitle=title;
        imageUrl = image;
        synopsis=overview;
        userRating=rating;
        releaseDate=release;
    }


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
}
