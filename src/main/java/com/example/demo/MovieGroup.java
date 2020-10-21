package com.example.demo;

import java.util.ArrayList;

public class MovieGroup extends MediaGroup{
    ArrayList<Movie> movies;

    public MovieGroup(String name){
        super(name);
        movies = new ArrayList<>();
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
