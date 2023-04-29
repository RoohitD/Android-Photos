package com.example.photosapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class imageView extends AppCompatActivity {

    ArrayList<Album> currentAlbum;
    ImageView imageSlide;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_slide);

        imageSlide = findViewById(R.id.imageSlide);
    }

    private void next(View view){

    }

    private void back(View view){

    }

    private void removePhoto(View view){

    }
}
