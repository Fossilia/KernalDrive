package com.example.demo;

import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;

import java.util.List;

public class Movie extends Media {

    private int tmdbID;

    private String filePath;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterPath;

    private List<Genre> genres;
    private List<PersonCast> cast;
    private List<PersonCrew> crew;

    private String posterBaseURL = "https://image.tmdb.org/t/p/";

    public Movie(String filePath, int tmdbID){
        this.tmdbID = tmdbID;
        this.filePath = filePath;
    }
    public int getTmdbID() {
        return tmdbID;
    }

    public void setTmdbID(int tmdbID) {
        this.tmdbID = tmdbID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<PersonCast> getCast() {
        return cast;
    }

    public void setCast(List<PersonCast> cast) {
        this.cast = cast;
    }

    public List<PersonCrew> getCrew() {
        return crew;
    }

    public void setCrew(List<PersonCrew> crew) {
        this.crew = crew;
    }

    public String getPosterBaseURL() {
        return posterBaseURL;
    }

    public void setPosterBaseURL(String posterBaseURL) {
        this.posterBaseURL = posterBaseURL;
    }
}
