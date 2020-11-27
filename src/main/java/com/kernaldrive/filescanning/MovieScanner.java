package com.kernaldrive.filescanning;

import com.kernaldrive.metadata.Movie;
import com.kernaldrive.metadata.MovieGroup;
import com.kernaldrive.metadata.TmdbFilenameSearch;
import com.kernaldrive.metadata.TmdbMovieExtractor;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieScanner {

    String[] validExtensions = {"mkv", "mp4", "webm", "avi"}; //hardcoded valid extension, there won't be many so thats why it isn't in a text file
    private TmdbFilenameSearch tmdbFilenameSearch;
    private TmdbMovieExtractor tmdbMovieExtractor;
    MovieDBManager database = null;

    public MovieScanner(){

        //initializing database
        try {
            database = new MovieDBManager();
            database.clearDatabaseScans(); //marks all media as unscanned
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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
        long startTime = System.nanoTime();
        for(File path: movieGroup.getPaths()) { //go through all the paths in the group
            scan(movieGroup, path); //scan the path
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("TOTAL SCAN TIME WAS: "+((float)duration/1000000000)+" SECONDS");
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
        int movieID = 0;
        try {
            Movie movie = null;
            ResultSet databaseMovieEntry = database.findMovie(file.getPath()); //attempt to find the movie in the database

            if(databaseMovieEntry == null){ //if the movie that was scanned was not found in the database
                try{
                    movieID = tmdbFilenameSearch.searchTmdb(file.getName()); //find the id of the movie based on the filename
                }
                catch (Exception e){
                    System.out.println("MOVIE COULD NOT BE FOUND ON TMBD: ["+file.getName()+"]");
                    return;
                }
                movie = tmdbMovieExtractor.extractMovieInfo(file.getPath(), movieID); //fetch movie info from id and create movie object
                System.out.println("DETECTED NEW MOVIE, ADDED TO DATABASE: ["+movie.getTitle()+"]");
                database.addMovie(movie); //add newly found movie to the database
            }
            else{ //if it was found
                movie = new Movie(databaseMovieEntry); //create a new movie object from the information in the database
                System.out.println("DETECTED MOVIE IN DATABASE: ["+movie.getTitle()+"].");
            }
            movieGroup.addMovie(movie); //add movie created from either case into the movie group
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
