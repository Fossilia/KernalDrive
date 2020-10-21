package com.example.demo;

public class Main {

    public static void main(String[] args) {

        TmdbFilenameSearch test = new TmdbFilenameSearch();
        TmdbMovieExtractor tmdbMovieExtractor = new TmdbMovieExtractor();

       /* int movieID = test.filenameParser("Back.to.the.Future.Part.II.1989.UHD.BluRay.2160p.HEVC.TrueHD.Atmos.7.1-BeyondHD");
        tmdbMovieExtractor.extractMovieInfo("", movieID);
        System.out.println();
        movieID = test.filenameParser("Back.to.the.Future.Part.III.1990.UHD.BluRay.2160p.HEVC.TrueHD.Atmos.7.1-BeyondHD [82.42GB]");
        tmdbMovieExtractor.extractMovieInfo("", movieID);*/

        MediaInfoExtractor mediaInfoTest = new MediaInfoExtractor("C:\\Users\\idree\\Videos\\MSETU.mkv");



    }
}