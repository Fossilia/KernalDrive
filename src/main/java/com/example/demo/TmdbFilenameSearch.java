package com.example.demo;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TmdbFilenameSearch implements TmdbKeys {

    private String filenameRegex = "^(.*?)\\W(?:(\\d{4}))";

    public TmdbFilenameSearch() {

    }

    //Change if they dont have a year in title later
    public int filenameParser(String filename) {
        String searchQuery = "";
        int yearQuery = 0;
        Pattern p = Pattern.compile(filenameRegex);
        Matcher m = p.matcher(filename);
        if (m.find()) {
            searchQuery = m.group(1).replaceAll("[.|_]", " ");
            if (m.group(2) != null) {
                yearQuery = Integer.parseInt(m.group(2));
            }
        }
        int movieID = searchTmdb(searchQuery, yearQuery);
        return movieID;
    }

    private int searchTmdb(String searchQuery, int yearQuery) {
        TmdbSearch search = new TmdbSearch(new TmdbApi(APIKey));
        try {
            MovieResultsPage movieResultsPage = search.searchMovie(searchQuery, yearQuery, language, false,
                    1);
            int movieId = movieResultsPage.getResults().get(0).getId();
            return movieId;
        } catch (Exception e) {
            System.out.println(searchQuery);
        }
        return 11;

    }

}
