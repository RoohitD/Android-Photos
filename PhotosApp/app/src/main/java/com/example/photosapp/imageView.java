package com.example.photosapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class imageView extends AppCompatActivity {

    ArrayList<Photo> currentAlbum;
    ImageView imageSlide;

    int albumIndex;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_slide);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            currentAlbum = (ArrayList<Photo>) bundle.getSerializable(addEditAlbum.ALBUM_PHOTO);
            albumIndex = bundle.getInt("albumIndex");
        }

        imageSlide = findViewById(R.id.imageSlide);
        imageSlide.setImageURI(currentAlbum.get(albumIndex).getImage());
    }

    private void next(View view){

    }

    private void back(View view){

    }

    private void removePhoto(View view){

    }
}
