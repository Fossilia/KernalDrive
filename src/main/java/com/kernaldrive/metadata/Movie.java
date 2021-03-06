package com.kernaldrive.metadata;

import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.ProductionCompany;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;

import com.serhatozdal.scraper.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Movie extends Media {

    private int tmdbID;
    private String filePath;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private String bannerPath;
    private String runtime;
    private String tagline;
    private String year;
    private String moviePagePosterPath;
    private String imdbKey;
    private Float imdbRating;

    private List<Genre> genres;
    private List<PersonCast> cast;
    private List<PersonCrew> crew;
    private List<ProductionCompany> productionCompanies;

    private String posterBaseURL = "https://image.tmdb.org/t/p/original";
    private Scraper imdbScraper;

    public Movie(String filePath, int tmdbID){
        this.tmdbID = tmdbID;
        this.filePath = filePath;
    }

    public Movie(int tmdbid, String title, String year, String genre, String cover, String path){
        this.tmdbID = tmdbid;
        this.title = title;
        this.year = year;
        //this.genre = genre;
        this.posterPath = cover;
        this.filePath = path;
    }

    public Movie(ResultSet data) throws SQLException {
        //if(data.next()){
        tmdbID = data.getInt(2);
        title = data.getString(3);
        year = data.getString(4);
        //genres = data.getString("genre");
        posterPath = data.getString(6);
        bannerPath = data.getString(7);
        moviePagePosterPath = data.getString(8);
        filePath = data.getString(9);
        //}
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
        setYear(releaseDate.substring(0,4));
    }
    public void setYear(String year){
        this.year = year;
    }

    public String getYear(){
        return year;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getPosterURL() {
        return posterBaseURL + posterPath;
    }

    public void setMoviePagePoster(String path){
        moviePagePosterPath = "https://image.tmdb.org/t/p/w780" + path;
    }

    public String getMoviePagePoster(){
        return moviePagePosterPath;
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

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = posterBaseURL + bannerPath;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        int hours = runtime / 60; //since both are ints, you get an int
        int minutes = runtime % 60;
        this.runtime = hours+"hr " + minutes + "min";
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public String getImdbKey() {
        return imdbKey;
    }

    public void setImdbKey(String imdbKey) {
        this.imdbKey = imdbKey;
        //System.out.println(this.imdbKey);
    }

    public Float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating() {
        Scraper test = new MediaScraper();
        com.serhatozdal.scraper.model.Media media = (com.serhatozdal.scraper.model.Media) test.findById(imdbKey);
        if(media.isFound()) {
            //System.out.println(media.getRating());
            this.imdbRating = media.getRating();
        }
        else{
            System.out.println("Not found");
        }

    }
}
