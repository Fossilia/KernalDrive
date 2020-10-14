package com.segmentationfault;

import java.io.File;

public class Movie {

    File file;
    String name;

    String folderName;

    public Movie(File file){
        this.file = file;
        name = file.getName();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
