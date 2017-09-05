package com.example.skolen.hotelapplikasjon.Adapter;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skolen.hotelapplikasjon.Model.Tourism;
import com.example.skolen.hotelapplikasjon.R;

import java.util.ArrayList;

/**
 * Created by Skolen on 15.05.2017.
 */

public class TourismAdapter extends ArrayAdapter<Tourism> {

    private Context mContext;
    private TypedArray images;

    public TourismAdapter(Context context, int textViewResourceId, ArrayList<Tourism> tourism, TypedArray image) {
        super(context, textViewResourceId, tourism);

        this.images = image;
        this.mContext = context;
    }

    private class ViewHolder {
        ImageView mImageView;
        TextView mTextName;
        TextView mTextAddress;
        TextView mTextDescription;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mLayoutInflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mLayoutInflate.inflate(R.layout.tourist_adapter_view, parent, false);
        }

        ViewHolder mViewHolder = new ViewHolder();

        mViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.tourist_image);
        mViewHolder.mTextName = (TextView) convertView.findViewById(R.id.tourist_name);
        mViewHolder.mTextAddress = (TextView) convertView.findViewById(R.id.tourist_address);
        mViewHolder.mTextDescription = (TextView) convertView.findViewById(R.id.tourist_description);
        Tourism tourism = getItem(position);

        if (tourism != null) {
            mViewHolder.mImageView.setImageResource(images.getResourceId(position, position));
            mViewHolder.mTextName.setText(tourism.getName());
            mViewHolder.mTextAddress.setText(tourism.getAddress());
            mViewHolder.mTextDescription.setText(tourism.getDescription());
        }

        return convertView;
    }
}
