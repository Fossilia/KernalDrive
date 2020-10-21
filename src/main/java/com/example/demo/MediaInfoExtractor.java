package com.example.demo;

import uk.co.caprica.vlcjinfo.MediaInfo;
import uk.co.caprica.vlcjinfo.Section;

public class MediaInfoExtractor {

    public String filePath;
    public MediaInfo mediaInfo;

    public MediaInfoExtractor(String filePath){
        this.filePath = filePath;
        this.mediaInfo = MediaInfo.mediaInfo(filePath);
    }

    public void extract(){
        Section video = mediaInfo.first("Video");
        System.out.println(video);
    }

    public void getBasic(){

    }

    public void getFull(){

    }

    public void getResolution(){
    }

}
