package com.segmentationfault;

import java.util.ArrayList;

public class MediaScanner {
    ArrayList<MediaGroup> mediaGroups;

    public MediaScanner(){
        mediaGroups = new ArrayList<>();
        getGroupInfo();

    }

    public void getGroupInfo(){
        System.out.println("Welcome to KernalDrive, please type in the name of your group:");
        String name = Input.getStringInput();
        MediaGroup group = new MediaGroup(name);
        System.out.println("Please input 1 if this a Movie group or input 2 if it is Shows group:");
        int type = Input.getIntInput(1, 2);
        group.setType(type);

    }
}
