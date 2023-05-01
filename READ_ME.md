
# Android Photos App

This app is a basic photos app for Android devices with Android 10.0+. 

## Authors

- Rohit Deshmukh (rrd82@scarletmail.rutgers.edu)

## Criteria

- Home screen. When the app comes up, it should load album and photo data from the previous session, if any, and list all albums with names in plain text. Off this "home" screen, you should be able to do the following, in one or more navigational steps.
- Open, create, delete, and rename albums as listed in the Java FX project description. When opening an album, display all its photos, with their thumbnail images.
- Once an album is open, you should be able to add, remove, or display a photo. The photo display screen should include an option for a slideshow, allowing you to go backward or forward in the album one photo at a time with manual controls.
- When a photo is displayed, you should be able to add a tag to a photo. Only person and location are valid tag types; there are no typeless tags. You should also be able to delete a tag from a photo. Note: When displaying a photo, tags (if any) should be visible.
- You should be able to move a photo from one album to another
- You should be able to search for photos by tag-value pairs, just like in the Photos assignment (of course now limited to only person and location type tags). All matches are case insensitive, so "new york" is the same as "nEw YOrk".

## Notes

- Add Album: For creating an album, click on the plus icon, in the top right corner.
    - When creating an album, click on the floating action button with the "+" sign to upload an image from the phone's files app. This photo will then get added to the album. And then, add an album name in the edit text field. 
    Note: Adding photos to an album is not required. But an album name is. So, an album can exist without any photos in it.
    
    Click on "save" button after that to save the album. Or else, the data will not be saved. 

- Delete Album: For deleting an album, long press on that album in the listView. This deletes the album.

- View Album: To view an album, tap on one of the albums and it leads to the same view as the add album view. The data for that album will also be loaded in it. 
    - Viewing Photos: You can tap on one of the images to see it in a larger view. This opens a new activity with the tags of the photo as well. 
        - Editing Tags: You can change the tags based on what you want and click "save" to save those changes. [Note: After editing tags, click on save in the album view, or else the changes won't be saved.]
        - Removing Photos: You can also click on "remove photo" to remove the photo from that album. [Note: After removing the photo, click on save in the album view, or else the changes won't be saved.]
        - Slideshow: Clicking on the "next" and "back" will show the next and previous image in the album. 
        - Adding Photos: As explained in Add Album above, you can add images to the album by clicking on the floating action button with the "+" sign. 
    [Note: After adding the photo, click on save in the album view, or else the changes won't be saved.]
        - Moving Photos: You can long click on an image, and an alert dialog, with the names of other albums, appears. You can click on one of the album to move the photo to that specific album. 
        [Note: After moving the photo, click on save in the album view, or else the changes won't be saved.]
- Searching Photos: In the homescreen of the app, you can click on the floating action button with a magnifying class in it to search for photos you have uploaded. 
    [Note: The search feature is a little buggy. So when you are done searching for one thing, you can click on the back button and then click the search button again to search.]