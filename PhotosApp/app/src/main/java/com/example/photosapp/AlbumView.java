package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AlbumView extends AppCompatActivity {

    FloatingActionButton fab_Photo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);

        fab_Photo = findViewById(R.id.fab_Photo);

        fab_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AlbumView.this, "Create new Photo", Toast.LENGTH_SHORT).show();
            }
        });

    }
}