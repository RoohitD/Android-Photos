package com.example.photosapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class addEditAlbum extends AppCompatActivity implements ImageAdapter.OnPhotoLongClickListener {

    private static final int REQUEST_CODE = 1;
    public static final int EDIT_PHOTO_REQUEST_CODE = 2;
    public static final String ALBUM_NAME = "albumName";
    public static final String ALBUM_PHOTO = "albumPhotos";
    public static final String ALBUM_INDEX = "albumIndex";
    public static final String PHOTO_TAGS = "photoTags";
    public static final String PHOTO_INDEX = "photoIndex";
    private int albumIndex;

    RecyclerView photoGrid;
    FloatingActionButton addPhoto;
    EditText albumName;
    Button saveButton;
    ArrayList<Photo> photos = new ArrayList<Photo>();

    ArrayList<Album> albums = new ArrayList<Album>();

    Album changedAlbum;

    int changedIndex = -1;

    private Toolbar myToolbar;

    ImageAdapter imageAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_album);

        photoGrid = findViewById(R.id.recycleGrid);
        addPhoto = findViewById(R.id.addPhotoButton);
        albumName = findViewById(R.id.albumName);
        saveButton = findViewById(R.id.saveButton);
        myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Add/Edit Album");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            albumIndex = bundle.getInt(ALBUM_INDEX);
            albumName.setText(bundle.getString(ALBUM_NAME));
            photos = (ArrayList<Photo>) bundle.getSerializable(ALBUM_PHOTO);
            albums = (ArrayList<Album>) bundle.getSerializable("albums");
        }

        imageAdapter = new ImageAdapter(this, photos);
        imageAdapter.setAlbumsList(albums);
        imageAdapter.setOnLongClickListener(this);
        photoGrid.setAdapter(imageAdapter);

        addPhoto.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Pictures"), REQUEST_CODE);
            imageAdapter.notifyDataSetChanged();
        });



    }

    ActivityResultLauncher<Intent> startForResultEdit =
    registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            applyPhotoEdit(result);
        }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null){

            Uri photoUri = data.getData();
            saveImagetoStorage(photoUri);
            Uri savedImage = getImagefromStorage(getFileNameFromUri(photoUri));
            Photo photo = new Photo(savedImage);
            photos.add(photo);
            imageAdapter.notifyDataSetChanged();
        } else if (resultCode == RESULT_OK && requestCode == EDIT_PHOTO_REQUEST_CODE && data != null){
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            ArrayList<Photo> newPhotos = (ArrayList<Photo>) bundle.getSerializable(ALBUM_PHOTO);
            photos.clear();
            photos.addAll(newPhotos);
            photoGrid.setAdapter(new ImageAdapter(this, photos));
        }
    }



    public void save(View view){
        String name = albumName.getText().toString();

        if(name == null || name.length() == 0){
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialogFragment.MESSAGE_KEY, "Album name is required");
            DialogFragment newFragment = new AlbumDialogFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(ALBUM_INDEX, albumIndex);
        if(changedIndex != -1 && changedAlbum != null){
            bundle.putInt("changedIndex", changedIndex);
            bundle.putSerializable("changedAlbum", changedAlbum);
        }
        bundle.putString(ALBUM_NAME, name);
        bundle.putSerializable(ALBUM_PHOTO, photos);
        bundle.putSerializable("albums", albums);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }


    void applyPhotoEdit(ActivityResult result){
        Intent intent = result.getData();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        String tags = bundle.getString(PHOTO_TAGS);
        int photoIndex = bundle.getInt(PHOTO_INDEX);
        photos = (ArrayList<Photo>) bundle.getSerializable(ALBUM_PHOTO);
        Photo photo = photos.get(photoIndex);
        photo.setTags(tags);
        photoGrid.setAdapter(new ImageAdapter(this, photos));
    }

    public void saveImagetoStorage(Uri imageUri){
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            String fileName = getFileNameFromUri(imageUri);
            File outputDir = getFilesDir();
            File outputFile = new File(outputDir, fileName);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Toast.makeText(this, String.valueOf(imageUri), Toast.LENGTH_SHORT).show();
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch(Exception e){
            Toast.makeText(this, "File not saved", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileNameFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        cursor.moveToFirst();
        String fileName = cursor.getString(column_index);
        cursor.close();
        return fileName;
    }

    public Uri getImagefromStorage(String uriPath){
        File file = new File(getFilesDir(), uriPath);
        return file.exists() ? Uri.fromFile(file) : null;
    }


    @Override
    public void onPhotoLongClick(Photo photo, View view) {
        // Display dialog with list of albums to choose from
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Move photo to album");

        String[] albumTitles = getAlbumTitles(); // Get titles of all albums
        builder.setItems(albumTitles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Move photo to selected album
                Photo updatedPhoto = new Photo(photo.getImage());
                updatedPhoto.setTags(photo.getTags());

                // Update RecyclerView
                int position = photos.indexOf(photo);
                photos.remove(position);
                albums.get(which).addPhoto(updatedPhoto);
                changedAlbum = albums.get(which);
                changedIndex = which;
                photoGrid.setAdapter(new ImageAdapter(view.getContext(), photos));
            }
        });

        builder.create().show();
    }


    private String[] getAlbumTitles() {
        ArrayList<String> albumTitles = new ArrayList<String>();

        // Add title of each album to the list
        for (Album album : albums) {
            albumTitles.add(album.toString());
        }

        // Convert list to array
        String[] titleArray = new String[albumTitles.size()];
        albumTitles.toArray(titleArray);

        return titleArray;
    }
}
