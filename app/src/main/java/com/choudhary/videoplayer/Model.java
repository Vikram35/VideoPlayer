package com.choudhary.videoplayer;

public class Model {

    String SongName, duration,filesize;


    public Model(String songName, String duration, String filesize) {
        SongName = songName;
        this.duration = duration;
        this.filesize = filesize;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }
}
