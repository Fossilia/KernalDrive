package com.segmentationfault;

import java.io.File;
import java.util.ArrayList;

public abstract class MediaGroup {
    String name;
    ArrayList<File> paths;

    public MediaGroup(String name){
        this.name = name;
        paths = new ArrayList<>();
    }

    /**
     * Used to add a File object to the paths list
     * @param path
     */
    public void addPath(File path){
        paths.add(path);
    }

    @Override
    public String toString() {
        return "MediaGroup{" +
                "name='" + name + '\'' +
                ", paths=" + paths +
                '}';
    }

    //just getter and setter classes past here

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<File> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<File> paths) {
        this.paths = paths;
    }
}
