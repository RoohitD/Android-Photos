package com.example.photosapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class imageGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Photo> mPhotos;

    public imageGridAdapter(Context context, ArrayList<Photo> photos) {
        mContext = context;
        mPhotos = photos;
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.image_view, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.layout.image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewHolder.imageView != null && mPhotos.get(position) != null) {
            Picasso.get().load(mPhotos.get(position).getImage()).into(viewHolder.imageView);
        }
        // Load image using Picasso or other image loading library


        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }
}

