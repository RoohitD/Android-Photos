package com.example.photosapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.PhotoViewHolder> implements Filterable{

    private static ArrayList<Photo> mPhotos;
    private ArrayList<Photo> filteredPhotoList = new ArrayList<Photo>();

    private boolean isFiltering = false;

    public ImageAdapter(ArrayList<Photo> photos) {
        mPhotos = photos;
        filteredPhotoList = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo currentItem = filteredPhotoList.get(position);
        holder.mImageView.setImageURI(currentItem.getImage());
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    @Override
    public Filter getFilter() {
        return new ImageFilter();
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
            ((Activity) view.getContext()).startActivityForResult(intent, addEditAlbum.EDIT_PHOTO_REQUEST_CODE);
        }

    }

    private class ImageFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Photo> filteredList = new ArrayList<Photo>();
            if(constraint == null || constraint.length() == 0){
                isFiltering = false;
                filteredList.addAll(mPhotos);
            } else {
                String query = constraint.toString().toLowerCase().trim();

                for (Photo photo: mPhotos){
                    for (String tag : photo.returnTags().values()) {
                        if (tag.toLowerCase().contains(query)) {
                            filteredList.add(photo);
                            break;
                        }
                    }
                }
                isFiltering = true;
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredPhotoList.clear();
                if(TextUtils.isEmpty(constraint)) {
                    filteredPhotoList.addAll(mPhotos);
                    notifyDataSetChanged();
                } else {
                    filteredPhotoList.addAll((ArrayList<Photo>) results.values);
                    notifyDataSetChanged();
                }


        }
    }
}

