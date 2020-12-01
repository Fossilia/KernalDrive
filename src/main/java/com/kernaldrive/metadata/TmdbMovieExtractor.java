package com.kernaldrive.metadata;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.ProductionCompany;
import info.movito.themoviedbapi.model.people.PersonCredits;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        //getOverview();
        getReleaseDate();
        getGenre();
        getPosters();
        getBanner();
        //getCast();
        //getCrew();
        //getProductionCompanies();
        //getRuntime();
        //getMpaaRating();
        //getTagline();
        getPosterURL();
        //getImdbId();

        return movie;
    }

    public Movie extractSecondaryInfo(Movie movie){
        this.movie = movie;
        this.movieID = movie.getTmdbID();
        this.movieDb = tmdbApi.getMovie(movieID, language, TmdbMovies.MovieMethod.credits, TmdbMovies.MovieMethod.images);
        //getTitle();
        getOverview();
        //getReleaseDate();
        getGenre();
        //getPosters();
        //getBanner();
        getCast();
        getCrew();
        getProductionCompanies();
        getRuntime();
        getMpaaRating();
        getTagline();
        //getPosterURL();
        getImdbId();

        return movie;

    }

    public void getTitle(){
        //System.out.println(movieDb.getTitle() +  " - " + movie.getFilePath());
        movie.setTitle(movieDb.getTitle());
    }

    public void getOverview(){
        //System.out.println(movieDb.getOverview());
        movie.setOverview(movieDb.getOverview());
    }

    public void getReleaseDate(){
        //System.out.println(movieDb.getReleaseDate());
        movie.setReleaseDate(movieDb.getReleaseDate());
    }

    public void getGenre(){
        //System.out.println(movieDb.getGenres());
        movie.setGenres(movieDb.getGenres());
    }

    public void getCast(){
        //System.out.println(movieDb.getCast());
        movie.setCast(movieDb.getCast());
    }

    public void getCrew(){
        //System.out.println(movieDb.getCrew());
        movie.setCrew(movieDb.getCrew());
    }

    public void getProductionCompanies(){
        //System.out.println(movieDb.getProductionCompanies());
        movie.setProductionCompanies(movieDb.getProductionCompanies());
    }

    private void getPosters(){
        //System.out.println("https://image.tmdb.org/t/p/original"+ movieDb.getPosterPath());
        try(InputStream in = new URL("https://image.tmdb.org/t/p/w185"+ movieDb.getPosterPath()).openStream()){
            Files.copy(in, Paths.get("./posters/"+movie.getTmdbID()+".jpg"));
        } catch (FileAlreadyExistsException e) {
            System.out.println("POSTER ALREADY EXISTS FOR: ["+movie.getTitle()+"]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        movie.setPosterPath("posters/"+movie.getTmdbID()+".jpg");
    }

    private void getPosterURL(){
        movie.setMoviePagePoster(movieDb.getPosterPath());
    }

    public void getBanner(){
        movie.setBannerPath(movieDb.getBackdropPath());
    }

    public void getRuntime(){
        movie.setRuntime(movieDb.getRuntime());
    }

    public void getTagline(){
        movie.setTagline(movieDb.getTagline());
        //System.out.println(movieDb.getTagline());
    }

    public void getImdbId(){
        movie.setImdbKey(movieDb.getImdbID());
        movie.setImdbRating();
    }

    public void getMpaaRating(){
    }

}