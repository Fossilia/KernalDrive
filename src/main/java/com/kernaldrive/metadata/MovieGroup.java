package com.kernaldrive.metadata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieGroup extends MediaGroup{
    ArrayList<Movie> movies;

    public MovieGroup(String name){
        super(name);
        movies = new ArrayList<>();
    }

    public MovieGroup(ResultSet group, ResultSet path) throws SQLException {
        super(group, path);
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
