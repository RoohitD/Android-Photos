package com.example.photosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.photosapp.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ArrayList<Album> albumList = new ArrayList<>();
    ArrayList<AlbumView> albumViews = new ArrayList<AlbumView>();

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

                AlbumView newAlbum = new AlbumView();
                albumViews.add(newAlbum);
                albumList.add(currentAlbum);
                arrayAdapter.notifyDataSetChanged();
            }
        });

       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   AlbumView openClass = albumViews.get(i);
                   startActivity(new Intent(MainActivity.this, openClass.getClass()));
               }
            }
       );

    }
}