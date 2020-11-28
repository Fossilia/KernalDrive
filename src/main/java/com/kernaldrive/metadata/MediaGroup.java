package com.kernaldrive.metadata;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class MediaGroup {
    String name;
    ArrayList<File> paths;

    public MediaGroup(String name){
        this.name = name;
        paths = new ArrayList<>();
    }

    /**
     * used to load a mediagroup from the database
     * @param group group row to load name off of
     * @param paths proup paths row(s) to load paths off of
     * @throws SQLException
     */
    public MediaGroup(ResultSet group, ResultSet paths) throws SQLException {
        this.paths = new ArrayList<>();
        name = group.getString(2);
        while(paths.next()){
            addPath(new File(paths.getString(3)));
        }
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
