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
        tmdbFilenameSearch = new TmdbFilenameSearch();
        tmdbMovieExtractor = new TmdbMovieExtractor();
    }

    /**
     * Scans all paths of a movie group and creates movie objects based on video files it finds in the groups paths
     * @param movieGroup movie group to be scanned
     */
    public void scan(MovieGroup movieGroup){
        for(File path: movieGroup.getPaths()){ //go through all the paths in the group
            for(File file : path.listFiles()){ //go through all files and directories in the current directory
                if(file.isFile()){ //check if it is a valid file (and not a folder)
                    for(String ext: validExtensions){ //go through all of the valid extensions
                        if(ext.toLowerCase().equals(getExtension(file))){ //check if the extensions are equal
                            addMovie(file, movieGroup);
                            break;
                        }
                    }
                }
                else if(file.isDirectory()){ //second level searching in case there is a folder in the path (only does one level, it is not recursive)
                    for(File deeperFile: file.listFiles()){
                        if(deeperFile.isFile()){
                            for(String ext: validExtensions){ //go through all of the valid extensions
                                if(ext.toLowerCase().equals(getExtension(deeperFile))){ //check if the extensions are equal

                                    //Movie movie = new Movie(deeperFile); //create the movie object
                                    //movie.setFolderName(file.getName());
                                    //movieGroup.addMovie(movie); //add t0he movie to the group
                                    //System.out.println(movie.getName());
                                    break;
                                }
                            }
                        }
                    }
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
