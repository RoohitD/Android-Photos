package com.example.photosapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    FloatingActionButton fab_Search;
    public static ArrayList<Album> albums = new ArrayList<Album>();

    ListView listView;

    Album currentAlbum;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            listView = findViewById(R.id.albumList);
            fab_Search = findViewById(R.id.fab);

            loadData();
            ArrayAdapter<Album> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,albums);
            listView.setAdapter(arrayAdapter);

            fab_Search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, searchPhoto.class);
                    //intent.putExtra(addEditAlbum.ALBUM_PHOTO, getAllPhotos());
                    startActivity(intent);
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
                                                        intent.putExtra(addEditAlbum.ALBUM_INDEX, i);
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
                    listView.setAdapter(new ArrayAdapter<Album>(MainActivity.this, android.R.layout.simple_list_item_1, albums));
                    saveData();
                    return false;
                }
            });
            registerActivities();




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
        int index = bundle.getInt(addEditAlbum.ALBUM_INDEX);
        if (addEdit.equals("edit")) {
            // update the album
            Album album = albums.get(index);
            album.setAlbumName(name);
            album.setAlbum(newAlbum);
        } else if (addEdit.equals("add")){
            albums.add(new Album(name, newAlbum));
        }
        // redo the adapter to reflect change
        listView.setAdapter(new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, albums));
        saveData();
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

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("albums", null);
        Type type = new TypeToken<ArrayList<Album>>() {}.getType();
        albums = gson.fromJson(json, type);

        if (albums == null) {
            albums = new ArrayList<Album>();
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(albums);
        editor.putString("albums", json);
        editor.apply();
    }


}