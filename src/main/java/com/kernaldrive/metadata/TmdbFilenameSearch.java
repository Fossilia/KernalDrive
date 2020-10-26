package com.kernaldrive.metadata;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TmdbFilenameSearch implements TmdbKeys {

    private TmdbSearch tmdbSearch;
    private String filenameRegex = "^(.*?)\\W(?:(\\d{4}))";
    private String searchQuery = "";
    private int yearQuery = 0;

    public TmdbFilenameSearch() {
        tmdbSearch = new TmdbSearch(new TmdbApi(APIKey));
    }

    //Change if they dont have a year in title later eg. Blade Runner 2049 (2019)
    private void filenameParser(String filename){
        Pattern p = Pattern.compile(filenameRegex);
        Matcher m = p.matcher(filename);
        if (m.find()) {
            searchQuery = m.group(1).replaceAll("[.|_]", " ");
            if (m.group(2) != null) {
                yearQuery = Integer.parseInt(m.group(2));
            }
        }
    }

    public int searchTmdb(String filename) throws Exception{
        filenameParser(filename);
        MovieResultsPage movieResultsPage = tmdbSearch.searchMovie(searchQuery, yearQuery, language, false, 1);
        int movieId = movieResultsPage.getResults().get(0).getId();
        return movieId;
    }

}
