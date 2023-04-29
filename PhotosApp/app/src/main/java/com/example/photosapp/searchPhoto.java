package com.example.photosapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class searchPhoto extends AppCompatActivity {



    RecyclerView filteredImages;
    ArrayList<Photo> allPhotos;
    SearchView searchView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_photo);

        filteredImages = findViewById(R.id.searchGrid);
        searchView = findViewById(R.id.searchView);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            allPhotos = (ArrayList<Photo>) bundle.getSerializable(addEditAlbum.ALBUM_PHOTO);
        }

    }
}
