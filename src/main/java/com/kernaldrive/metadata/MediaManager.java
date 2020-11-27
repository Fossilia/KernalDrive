package com.kernaldrive.metadata;

import com.kernaldrive.filescanning.Input;
import com.kernaldrive.filescanning.MovieScanner;

import java.io.File;
import java.util.ArrayList;

public class MediaManager {
    ArrayList<MovieGroup> movieGroups;
    ArrayList<ShowGroup> showGroups;
    private MovieScanner scanner;

    public MediaManager(){
        scanner = new MovieScanner();
        movieGroups = new ArrayList<>();
        String input = "";
        System.out.println("Welcome to KernalDrive!");
        while(!input.equals("no")){
            getNewGroupInfo();
            System.out.println("Would you like to add another group? Input 'yes' or 'no':");
            input = Input.getStringInput();
        }
        System.out.println("Groups inputted: "+ movieGroups);
    }

    public ArrayList<MovieGroup> getMovieGroups() {
        return movieGroups;
    }

    public void setMovieGroups(ArrayList<MovieGroup> movieGroups) {
        this.movieGroups = movieGroups;
    }

    public ArrayList<ShowGroup> getShowGroups() {
        return showGroups;
    }

    public void setShowGroups(ArrayList<ShowGroup> showGroups) {
        this.showGroups = showGroups;
    }

    /**
     *
     */
    public void getNewGroupInfo(){
        System.out.println("please type in the name of your group:");
        String name = Input.getStringInput();
        System.out.println("Please input [1] if this a Movie group or input [2] if it is Shows group:");
        int type = Input.getIntInput(1, 2);
        if(type == 1){ //if movie
            MovieGroup group = new MovieGroup(name);
            addPaths(group);
            //MovieScanner scanner = new MovieScanner();
            //scanner.scan(group);
            movieGroups.add(group);
        }
        else{ //if show
            //show scanning to be implemented later
        }
    }

    /**
     * loops to get as many paths the user wants to enter into the group given
     * @param group adds paths to this media group
     */
    public void addPaths(MovieGroup group){
        String input = "";
        System.out.println("Type each path on your drive you want this group to scan media files from (eg. C:/Users/name/Downloads):");
        while(!input.equals("exit")){
            System.out.println("add as many paths as you want, once you finish type in 'exit'.");
            input = Input.getValidPath();
            if(input.equals("exit")){
                continue;
            }
            File path = new File(input);
            group.addPath(path);
        }
        System.out.println("The paths you added are: "+group.getPaths());
    }

    public void scanGroups(int pos){
        scanner.scan(movieGroups.get(pos));
        /*for (int i = 0; i < movieGroups.size(); i++) {
            MovieScanner scanner = new MovieScanner();
            scanner.scan(movieGroups.get(i));
        }*/
    }
}
