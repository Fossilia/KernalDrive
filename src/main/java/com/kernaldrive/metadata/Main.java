package com.kernaldrive.metadata;

public class Main {

    public static void main(String[] args) {

        TmdbFilenameSearch test = new TmdbFilenameSearch();
        TmdbMovieExtractor tmdbMovieExtractor = new TmdbMovieExtractor();

        try {
            //int movieID = test.searchTmdb("The Lord of the Rings The Two Towers (2002) Theatrical Cut [1080p BluRay DTS-HD MA 6.1 VC-1 REMUX FraMeSToR].mkv");
            int movieID = test.searchTmdb("7878vd hj");

            tmdbMovieExtractor.extractMovieInfo("", movieID);
        }
        catch (Exception e){
            System.out.println("Movie not found!");
        }

        //movieID = test.searchTmdb("Cars (2006)");
        //tmdbMovieExtractor.extractMovieInfo("", movieID);

    }
}