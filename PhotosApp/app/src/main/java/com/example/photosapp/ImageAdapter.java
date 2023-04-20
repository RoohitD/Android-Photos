package com.example.photosapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.photosapp.Photo;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    public ArrayList<Photo> imageArray = new ArrayList<Photo>();

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }

    @Override
    public Object getItem(int i) {
        return imageArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView image = new ImageView(mContext);
        image.setImageURI(imageArray.get(i).getImage());
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setLayoutParams(new ViewGroup.LayoutParams(340,350));
        return image;
    }
}
