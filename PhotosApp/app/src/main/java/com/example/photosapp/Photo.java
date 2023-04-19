package classes;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
    @author Rohit Deshmukh
    The Photo class represents a photo object in the photo album application.
    Each photo object has a caption, an image file, a set of tags, and a date.
*/

public class Photo implements Serializable {
    
    private String caption;
    private File image;
    private HashMap<String, String> tags;
    private Date date;

    /**
     * Constructs a new Photo object with the specified caption, image file, and tags.
     * 
     * @param caption the caption of the photo
     * @param image the image file of the photo
     * @param tags the tags of the photo, in the format "tag1: value1, tag2: value2, ..."
     */
    public Photo(String caption, File image, String tags){
        this.image = image;
        this.caption = caption;
        this.tags = convertTags(tags);
        this.date = getLastModifiedDate();
    }

    /**
     * Returns the date the photo was last modified.
     * @return the date the photo was last modified
     */
    private Date getLastModifiedDate() {
        Calendar cal = Calendar.getInstance();
        long lastModifiedTime = image.lastModified();
        cal.setTimeInMillis(lastModifiedTime);
        return cal.getTime();
    }
    
    /**
     * Returns the caption of the photo.
     * 
     * @return the caption of the photo
     */
    public String toString(){
        return caption;
    }

    /**
     * Returns the caption of the photo.
     * 
     * @return the caption of the photo
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Returns the image file of the photo.
     * 
     * @return the image file of the photo
     */
    public File getImage() {
        return image;
    }

    /**
     * Returns the tags of the photo, in the format "tag1: value1, tag2: value2, ...".
     * 
     * @return the tags of the photo
     */
    public String getTags() {
        StringBuilder sb = new StringBuilder();
        for (String key : this.tags.keySet()) {
            String value = this.tags.get(key);
            sb.append(key).append(": ").append(value).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()); // remove the last ", "
        return sb.toString();
    }


    /**
     * Returns the date of the photo.
     * 
     * @return the date of the photo
     */
    public Date getDate(){
        return date;
    }

    /**
     * Sets the caption of the photo.
     * 
     * @param caption the new caption of the photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
        //User.save();
    }

    /**
     * Edits the tags of the photo.
     * 
     * @param tags the new tags of the photo, in the format "tag1: value1, tag2: value2, ..."
     */
    public void editTags(String tags) {
        this.tags = convertTags(tags);
        //User.save();
    }

    /**
     * Converts a string of comma-separated key-value pairs into a HashMap.
     * @param tagsString a string of comma-separated key-value pairs
     * @return a HashMap containing the key-value pairs in the input string
     */
    public static HashMap<String, String> convertTags(String tagsString) {
        HashMap<String, String> tags = new HashMap<>();
        String[] tagPairs = tagsString.split(", ");
        for (String tagPair : tagPairs) {
            String[] keyValue = tagPair.split(": ");
            tags.put(keyValue[0], keyValue[1]);
        }
        return tags;
    }

    /**
     * Returns a HashMap of the photo's tags.
     * @return a HashMap of the photo's tags
     */
    public HashMap<String, String> returnTags(){
        return this.tags;
    }

}
