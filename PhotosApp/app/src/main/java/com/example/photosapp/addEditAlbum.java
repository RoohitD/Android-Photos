package com.example.photosapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class addEditAlbum extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    public static final String ALBUM_NAME = "albumName";
    public static final String ALBUM_PHOTO = "albumPhotos";
    public static final String ALBUM_INDEX = "movieIndex";
    private int albumIndex;

    RecyclerView photoGrid;
    FloatingActionButton addPhoto;
    EditText albumName;
    Button saveButton;
    ArrayList<Photo> photos = new ArrayList<Photo>();

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
        //setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            albumIndex = bundle.getInt(ALBUM_INDEX);
            albumName.setText(bundle.getString(ALBUM_NAME));
            photos = (ArrayList<Photo>) bundle.getSerializable(ALBUM_PHOTO);
        }

        imageAdapter = new ImageAdapter(photos);
        photoGrid.setAdapter(imageAdapter);

        addPhoto.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Pictures"), REQUEST_CODE);
            imageAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null){

            Uri photoUri = data.getData();


            Photo photo = new Photo(photoUri);
            photos.add(photo);

            imageAdapter.notifyDataSetChanged();
        }
    }

    public void save(View view){
        String name = albumName.getText().toString();
        ArrayList<Photo> photo = photos;

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
        bundle.putString(ALBUM_NAME, name);
        bundle.putSerializable(ALBUM_PHOTO, photo);

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

}
