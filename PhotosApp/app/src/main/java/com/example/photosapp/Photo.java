package com.example.photosapp;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Photo implements Serializable {

    private String caption;
    private String photo;
    private HashMap<String, String> tags;
    private Date date;

    public Photo(Uri photo){
        this.caption = photo.toString();
        this.photo = photo.toString();
    }

    public Uri getImage(){
        return Uri.parse(photo);
    }

    public String toString(){
        return caption;
    }


}
