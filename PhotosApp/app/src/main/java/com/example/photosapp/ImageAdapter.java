package com.example.photosapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.PhotoViewHolder> implements Filterable{

    private static ArrayList<Photo> mPhotos;
    private ArrayList<Photo> filteredList;

    public ImageAdapter(ArrayList<Photo> photos) {
        mPhotos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo currentItem = mPhotos.get(position);
        holder.mImageView.setImageURI(currentItem.getImage());
        holder.mImageView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }



    public static class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView mImageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.photo_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(view.getContext(), imageView.class);
            intent.putExtra(addEditAlbum.ALBUM_PHOTO, mPhotos);
            intent.putExtra("albumIndex", position);
            view.getContext().startActivity(intent);

        }
    }
}

