package com.anand.movie_catalog_service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)  
public class Movie {
    private String movieId;
    private String title; 
    private String overview; 

    public Movie() {} 

    public Movie(String movieId, String title, String overview) {
        this.movieId = movieId;
        this.title = title;
        this.overview = overview;
    }

    // Getters and Setters
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
