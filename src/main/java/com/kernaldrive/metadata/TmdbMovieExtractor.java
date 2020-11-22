package com.kernaldrive.metadata;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCredits;

import java.util.List;


public class TmdbMovieExtractor implements TmdbKeys{

    private TmdbMovies tmdbApi;
    private MovieDb movieDb;
    private Movie movie;
    private int movieID;

    public TmdbMovieExtractor(){
        tmdbApi = new TmdbApi(APIKey).getMovies();
    }

    public Movie extractMovieInfo(String filepath, int movieID){
        this.movie = new Movie(filepath, movieID);
        this.movieID = movieID;
        this.movieDb = tmdbApi.getMovie(movieID, language, TmdbMovies.MovieMethod.credits, TmdbMovies.MovieMethod.images);
        getTitle();
        getOverview();
        getReleaseDate();
        getGenre();
        getPosters();
        getBanner();
        getCast();
        getCrew();
        getProductionCompanies();
        getRuntime();
        getMpaaRating();
        getTagline();

        return movie;
    }

    public void getTitle(){
        System.out.println(movieDb.getTitle() +  " - " + movie.getFilePath());
        movie.setTitle(movieDb.getTitle());
    }

    public void getOverview(){
        System.out.println(movieDb.getOverview());
        movie.setOverview(movieDb.getOverview());
    }

    public void getReleaseDate(){
        System.out.println(movieDb.getReleaseDate());
        movie.setReleaseDate(movieDb.getReleaseDate());
    }

    public void getGenre(){
        System.out.println(movieDb.getGenres());
        movie.setGenres(movieDb.getGenres());
    }

    public void getCast(){
        System.out.println(movieDb.getCast());
        movie.setCast(movieDb.getCast());
    }

    public void getCrew(){
        System.out.println(movieDb.getCrew());
        movie.setCrew(movieDb.getCrew());
    }

    public void getProductionCompanies(){
        //System.out.println(movieDb.getProductionCompanies());
    }

    private void getPosters(){
        System.out.println("https://image.tmdb.org/t/p/original"+ movieDb.getPosterPath());
        movie.setPosterPath(movieDb.getPosterPath());
    }
    public void getBanner(){
        movie.setBannerPath(movieDb.getBackdropPath());
    }

    public void getRuntime(){
        movie.setRuntime(movieDb.getRuntime());
    }

    public void getTagline(){
        movie.setTagline(movieDb.getTagline());
        System.out.println(movieDb.getTagline());
    }

    public void getMpaaRating(){
    }

}
