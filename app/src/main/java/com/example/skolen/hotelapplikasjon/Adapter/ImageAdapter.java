package com.example.skolen.hotelapplikasjon.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.skolen.hotelapplikasjon.R;

/**
 * Created by Skolen on 06.05.2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private Integer imageId[] = {R.drawable.sample_0,
            R.drawable.sample_1, R.drawable.sample_2,
            R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5};

    public ImageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return imageId.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;

        if (convertView == null) {
            img = new ImageView(mContext);
            img.setLayoutParams(new GridView.LayoutParams(300,300));
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            img.setPadding(0, 0, 0, 0);
        }
        else {
            img = (ImageView) convertView;
        }

        img.setImageResource(imageId[position]);

        return img;
    }
}
