package com.segmentationfault;

import java.io.File;
import java.util.ArrayList;

public class MediaScanner {
    ArrayList<MediaGroup> mediaGroups;

    public MediaScanner(){
        mediaGroups = new ArrayList<>();
        getGroupInfo();
    }

    /**
     * Creates a ne
     */
    public void getGroupInfo(){
        System.out.println("Welcome to KernalDrive, please type in the name of your group:");
        String name = Input.getStringInput();
        MediaGroup group = new MediaGroup(name);
        System.out.println("Please input [1] if this a Movie group or input [2] if it is Shows group:");
        int type = Input.getIntInput(1, 2);
        group.setType(type);
        addPaths(group);
    }

    /**
     * loops to get as many paths the user wants to enter into the group given
     * @param group adds paths to this media group
     */
    public void addPaths(MediaGroup group){
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
}
