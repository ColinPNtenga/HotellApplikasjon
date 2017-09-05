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

import com.example.skolen.hotelapplikasjon.Model.Room;
import com.example.skolen.hotelapplikasjon.R;

import java.util.ArrayList;

/**
 * Created by Skolen on 16.05.2017.
 */

public class RoomAdapter extends ArrayAdapter<Room> {

    private Context mContext;
    private TypedArray images;

    public RoomAdapter(Context context, int textViewResourceId, ArrayList<Room> room, TypedArray images) {
        super(context, textViewResourceId, room);

        this.mContext = context;
        this.images = images;
    }

    private class ViewHolder {
        ImageView mImageView;
        TextView mTextType;
        TextView mTextNumber;
        TextView mTextPrice;
        TextView mTextDescription;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mLayoutInflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mLayoutInflate.inflate(R.layout.room_adapter_view, parent, false);
        }

        ViewHolder mViewHolder = new ViewHolder();

        mViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.room_image);
        mViewHolder.mTextType = (TextView) convertView.findViewById(R.id.roomType);
        mViewHolder.mTextNumber = (TextView) convertView.findViewById(R.id.roomNumber);
        mViewHolder.mTextPrice = (TextView) convertView.findViewById(R.id.roomPrice);
        mViewHolder.mTextDescription = (TextView) convertView.findViewById(R.id.roomDescription);

        Room room = getItem(position);

        if (room != null) {
            mViewHolder.mImageView.setImageResource(images.getResourceId(position, position));
            mViewHolder.mTextType.setText(room.getRoomType());
            mViewHolder.mTextNumber.setText(Integer.toString(room.getRoomNumber()));
            mViewHolder.mTextPrice.setText(room.getRoomPrice());
            mViewHolder.mTextDescription.setText(room.getDescription());
        }

        return convertView;
    }
}
