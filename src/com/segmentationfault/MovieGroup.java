package com.segmentationfault;

import java.io.File;
import java.util.ArrayList;

public class MovieGroup extends MediaGroup{
    ArrayList<Movie> movies;

    public MovieGroup(String name){
        super(name);
    }

    public void addMovie(Movie movie){
        movies.add(movie);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setShows(ArrayList<Movie> movies) {
        this.movies = movies;
    }

}