package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AlbumView extends AppCompatActivity {

    FloatingActionButton fab_Photo;
    GridView photoGrid;
    ArrayList<classes.Photo> photoList;
    ArrayAdapter<classes.Photo> gridAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);

        fab_Photo = findViewById(R.id.fab_Photo);
        photoGrid = findViewById(R.id.imageGrid);
        photoList = new ArrayList<classes.Photo>();
        gridAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, photoList);
        photoGrid.setAdapter(gridAdapter);
        fab_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AlbumView.this, "Create new Photo", Toast.LENGTH_SHORT).show();
                selectImage();
            }
        });
    }

    static final int REQUEST_IMAGE_GET = 1;

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            classes.Photo newPhoto = new classes.Photo(fullPhotoUri);
            photoList.add(newPhoto);
            gridAdapter.notifyDataSetChanged();
        }
    }

}