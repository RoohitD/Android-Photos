package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import classes.Photo;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ArrayList<Album> albumList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView list = findViewById(R.id.listview);
        fab = findViewById(R.id.fab);
        Album currentAlbum = new Album("NewAlbum", new ArrayList<Photo>());


        ArrayAdapter<Album> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,albumList);
        list.setAdapter(arrayAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View view) {
                albumList.add(currentAlbum);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, currentAlbum.getAlbumName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}