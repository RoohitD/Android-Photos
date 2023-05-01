package com.example.photosapp;

import java.io.Serializable;
import java.util.ArrayList;
import com.example.photosapp.Photo;

/**
 *   Author: Rohit Deshmukh
 *  The Album class represents an album in the photo management application.
 *  An album has a name and a list of photos.
 */

import java.util.ArrayList;

public class Album implements Serializable{

    private String albumName;
    private ArrayList<Photo> photoList;

    public Album(String albumName, ArrayList<Photo> photoList){
        this.albumName = albumName;
        if(photoList == null){
            photoList = new ArrayList<Photo>();
        } else {
            this.photoList = photoList;
        }
    }

    public String toString(){
        return albumName;
    }

    public ArrayList<Photo> getAlbum(){
        return photoList;
    }

    public void addPhoto(Photo p){
        photoList.add(p);
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void removePhoto(Photo photo){
        photoList.remove(photo);
    }

    public void setAlbum(ArrayList<Photo> newAlbum){
        photoList.clear();
        photoList.addAll(newAlbum);
    }
}
