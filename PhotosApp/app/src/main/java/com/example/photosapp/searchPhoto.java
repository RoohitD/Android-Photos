package com.example.photosapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.photosapp.Photo;
import java.util.ArrayList;

public class searchPhoto extends AppCompatActivity {



    RecyclerView filteredImages;
    ArrayList<Photo> allPhotos;
    SearchView searchView;
    Toolbar myToolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_photo);

        filteredImages = findViewById(R.id.searchGrid);
        searchView = findViewById(R.id.searchView);
        myToolbar = findViewById(R.id.searchTool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            allPhotos = (ArrayList<Photo>) bundle.getSerializable(addEditAlbum.ALBUM_PHOTO);
        }

        ImageAdapter searchAdapter = new ImageAdapter(getAllPhotos());
        filteredImages.setAdapter(searchAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return true;
            }
        });

    }

    private ArrayList<Photo> getAllPhotos() {
        ArrayList<Photo> allPhotos = new ArrayList<Photo>();
        for(Album album: MainActivity.albums){
            for(Photo photo: album.getAlbum()){
                allPhotos.add(photo);
            }
        }
        return allPhotos;
    }


}
