package com.segmentationfault;

import java.io.File;

public class MovieScanner {
    public MovieScanner(){

    }

    public void scan(MovieGroup movieGroup){
        for(File path: movieGroup.getPaths()){
            for(File file : path.listFiles()){
                if(file.isFile() && (getExtension(file).toLowerCase().equals("mkv") || getExtension(file).toLowerCase().equals("mp4"))){
                    Movie movie = new Movie(file);
                    movieGroup.addMovie(movie);
                }
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
}
