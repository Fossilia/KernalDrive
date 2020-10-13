package com.segmentationfault;

import java.io.File;
import java.util.ArrayList;

public class MediaGroup {
    String name;
    int type;
    ArrayList<File> paths;

    public MediaGroup(String name){
        this.name = name;
        paths = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<File> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<File> paths) {
        this.paths = paths;
    }
}
