package com.example.photosapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *   Author: Rohit Deshmukh
 *  The Album class represents an album in the photo management application.
 *  An album has a name and a list of photos.
 */

public class Album implements Serializable {

    /** The name of the album. */
    private String albumName;

    /** The list of photos in the album. */
    private ArrayList<classes.Photo> albumPhotos = new ArrayList<classes.Photo>();

    /**
     * Constructs an album object with a specified name and list of photos.
     * @param albumName the name of the album
     * @param albumPhotos the list of photos in the album
     */

    public Album(String albumName, ArrayList<classes.Photo> albumPhotos){
        this.albumName = albumName;
        if(albumPhotos == null){
            albumPhotos = new ArrayList<classes.Photo>();
        } else {
            for(int i = 0; i < albumPhotos.size(); i++){
                this.albumPhotos.add(albumPhotos.get(i));
            }
        }
    }

    /**
     * Returns the name of the album.
     * @return the name of the album
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * Returns the list of photos in the album.
     * @return the list of photos in the album
     */
    public ArrayList<classes.Photo> getAlbumPhotos() {
        return albumPhotos;
    }

    /**
     * Sets the name of the album.
     * @param albumName the new name for the album
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * Sets the list of photos in the album.
     * @param albumPhotos the new list of photos for the album
     */
    public void setAlbumPhotos(ArrayList<classes.Photo> albumPhotos) {
        for(int i = 0; i < albumPhotos.size(); i++){
            this.albumPhotos.add(albumPhotos.get(i));
        }
    }

    /**
     * Adds a photo to the album.
     * @param photo the photo to add to the album
     */

    public void addPhotoToAlbum(classes.Photo photo){
        albumPhotos.add(photo);
    }

    /**
     * Replaces the list of photos in the album with a new list.
     * @param albumPhotos the new list of photos for the album
     */
    public void editAlbum(ArrayList<classes.Photo> albumPhotos){
        albumPhotos.clear();
        for(int i = 0; i < albumPhotos.size(); i++){
            this.albumPhotos.add(albumPhotos.get(i));
        }
    }


    /**
     * Returns a string representation of the album.
     * @return a string representation of the album
     */
    public String toString(){
        return albumName + " (" + albumPhotos.size() + ") ";
    }

     /**
     * Returns the album object.
     * @return the album object
     */
    public Album getAlbum(){
        return this;
    }

    /**
     * Removes a photo from the album.
     * @param photo the photo to remove from the album
     */
    public void removePhoto(classes.Photo photo){
        albumPhotos.remove(photo);
    }
    

}
