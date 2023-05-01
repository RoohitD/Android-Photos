package com.example.photosapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class imageView extends AppCompatActivity {

    ArrayList<Photo> currentAlbum;

    ImageView imageSlide;
    EditText photoTag;
    int albumIndex;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_slide);
        photoTag = findViewById(R.id.photoTag);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            currentAlbum = (ArrayList<Photo>) bundle.getSerializable(addEditAlbum.ALBUM_PHOTO);
            albumIndex = bundle.getInt("albumIndex");
        }

        imageSlide = findViewById(R.id.imageSlide);
        imageSlide.setImageURI(currentAlbum.get(albumIndex).getImage());
        photoTag.setText(currentAlbum.get(albumIndex).getTags());

    }

    public void next(View view){
        albumIndex++;
        if(albumIndex < 0 || albumIndex > currentAlbum.size()){
            albumIndex--;
        } else {
            imageSlide.setImageURI(currentAlbum.get(albumIndex).getImage());
            photoTag.setText(currentAlbum.get(albumIndex).getTags());
        }
    }

    public void back(View view){
        albumIndex--;
        if (albumIndex < 0 || albumIndex > currentAlbum.size()){
            albumIndex++;
        } else {
            imageSlide.setImageURI(currentAlbum.get(albumIndex).getImage());
            photoTag.setText(currentAlbum.get(albumIndex).getTags());
        }
    }

    public void removePhoto(View view){
        currentAlbum.remove(albumIndex);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(addEditAlbum.ALBUM_PHOTO,currentAlbum);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void save(View view){
        currentAlbum.get(albumIndex).setTags(photoTag.getText().toString());
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(addEditAlbum.ALBUM_PHOTO,currentAlbum);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }



}
