package com.example.photosapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_Search;
    ArrayList<Album> albums = new ArrayList<Album>();

    ListView listView;

    Album currentAlbum;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.albumList);
        fab_Search = findViewById(R.id.fab);


        ArrayAdapter<Album> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,albums);
        listView.setAdapter(arrayAdapter);
        fab_Search.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, searchPhoto.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(addEditAlbum.ALBUM_PHOTO, getAllPhotos());
                startActivity(intent, bundle);
            }
        });

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   try {
                       currentAlbum = albums.get(i);
                       Intent intent = new Intent(MainActivity.this, addEditAlbum.class);
                       intent.putExtra("albumName", albums.get(i).toString());
                       intent.putExtra("albumPhotos", albums.get(i).getAlbum());
                       startForResultEdit.launch(intent);
                   } catch (Exception e) {
                       throw new RuntimeException(e);
                   }
               }
            }
       );

       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                albums.remove(i);
                arrayAdapter.notifyDataSetChanged();
                return false;
           }
       });

       registerActivities();
    }

    private ArrayList<Photo> getAllPhotos() {
        ArrayList<Photo> allPhotos = new ArrayList<Photo>();
        for(Album album: albums){
            allPhotos.addAll(album.getAlbum());
        }
        return allPhotos;
    }

    private ActivityResultLauncher<Intent> startForResultEdit;
    private ActivityResultLauncher<Intent> startForResultAdd;

    public void registerActivities() {
        startForResultEdit =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                applyEdit(result, "edit");
                            }
                        });

        startForResultAdd =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                applyEdit(result, "add");
                            }
                        });

    }

    private void applyEdit(ActivityResult result, String addEdit) {
        Intent intent = result.getData();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        // gather all info passed back by launched activity
        String name = bundle.getString(addEditAlbum.ALBUM_NAME);
        ArrayList<Photo> newAlbum = (ArrayList<Photo>) bundle.getSerializable("albumPhotos");
        int index = bundle.getInt("index");
        if (addEdit.equals("edit")) {
            // update the movie
            Album album = albums.get(index);
            album.setAlbumName(name);
            for (int i = 0; i < newAlbum.size(); i++){
                album.addPhoto(newAlbum.get(i));
            }
        } else if (addEdit.equals("add")){
            albums.add(new Album(name, newAlbum));
        }

        //Toast.makeText(this, newAlbum.size(), Toast.LENGTH_SHORT).show();

        // redo the adapter to reflect change
        listView.setAdapter(new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, albums));
    }

    private void showAlbum(int pos){
        Bundle bundle = new Bundle();
        Album album = albums.get(pos);
        bundle.putInt(addEditAlbum.ALBUM_INDEX, pos);
        bundle.putString(addEditAlbum.ALBUM_NAME, album.toString());
        bundle.putSerializable(addEditAlbum.ALBUM_PHOTO, album.getAlbum());

        Intent intent = new Intent(this, addEditAlbum.class);
        intent.putExtras(bundle);
        startForResultEdit.launch(intent);
    }

    private void addAlbum(){
        Intent intent = new Intent(this, addEditAlbum.class);
        startForResultAdd.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addAlbum();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}