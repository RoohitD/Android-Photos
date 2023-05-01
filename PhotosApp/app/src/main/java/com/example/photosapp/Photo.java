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
        setTags("Location: None, People: None");
    }

    public Uri getImage(){
        return Uri.parse(photo);
    }

    public String toString(){
        return caption;
    }

    public String getTags() {
        StringBuilder sb = new StringBuilder();
        for (String key : this.tags.keySet()) {
            String value = this.tags.get(key);
            sb.append(key).append(": ").append(value).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()); // remove the last ", "
        return sb.toString();
    }

    public void setTags(String tags){
        this.tags = convertTags(tags);
    }
    public static HashMap<String, String> convertTags(String tagsString) {
        HashMap<String, String> tags = new HashMap<>();
        String[] tagPairs = tagsString.split(", ");
        for (String tagPair : tagPairs) {
            String[] keyValue = tagPair.split(": ");
            tags.put(keyValue[0], keyValue[1]);
        }
        return tags;
    }

    public HashMap<String, String> returnTags(){
        return tags;
    }

}
