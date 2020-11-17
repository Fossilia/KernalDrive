package com.kernaldrive.filescanning;

import com.kernaldrive.metadata.Movie;
import com.kernaldrive.metadata.MovieGroup;
import com.kernaldrive.metadata.TmdbFilenameSearch;
import com.kernaldrive.metadata.TmdbMovieExtractor;

import java.io.File;

public class MovieScanner {

    String[] validExtensions = {"mkv", "mp4", "webm", "avi"}; //hardcoded valid extension, there won't be many so thats why it isn't in a text file
    private TmdbFilenameSearch tmdbFilenameSearch;
    private TmdbMovieExtractor tmdbMovieExtractor;

    public MovieScanner(){
        try {
            tmdbFilenameSearch = new TmdbFilenameSearch();
            tmdbMovieExtractor = new TmdbMovieExtractor();
        }
        catch (Exception e){
            System.out.println("Could not connect to TMBD, please check your internet connection");
        }
    }

    /**
     * Loops through each path and calls the second scan method to scan each of them
     * @param movieGroup movie group to be scanned
     */
    public void scan(MovieGroup movieGroup){
        for(File path: movieGroup.getPaths()) { //go through all the paths in the group
            scan(movieGroup, path); //scan the path
        }
    }

    /**
     * Scans the inputted path recursively
     * @param movieGroup movie group to be scanned
     * @param path current path from moviegroup being scanned
     */
    public void scan(MovieGroup movieGroup, File path){
        for(File file : path.listFiles()) { //go through all files and directories in the current directory
            if (file.isFile()) { //check if it is a valid file (and not a folder)
                for (String ext : validExtensions) { //go through all of the valid extensions
                    if (ext.toLowerCase().equals(getExtension(file))) { //check if the extensions are equal
                        //System.out.println(file.getName());
                        addMovie(file, movieGroup);
                        break;
                    }
                }
            }
            else if (file.isDirectory()) { //if the file detected is a folder
                scan(movieGroup, file); //recursively repeat scan with new folder
            }
        }
    }



    /**
     * Gets the extension of a file
     * @param f the file to get extension from
     * @return extension of file if it has one, otherwise null
     */
    public static String getExtension(File f){
        String path = f.getName();
        if(path.lastIndexOf(".")!=-1){
            return path.substring(path.lastIndexOf(".")+1);
        }
        else{
            return null;
        }
    }

    public void addMovie(File file, MovieGroup movieGroup){
        try {

            int movieID = tmdbFilenameSearch.searchTmdb(file.getName());
            Movie movie = tmdbMovieExtractor.extractMovieInfo(file.getPath(), movieID);
            movieGroup.addMovie(movie);
        }
        catch (Exception e){
            //System.out.println("Movie not found!");
        }
        //System.out.println("------------------------------------------------------------------");
    }
}
