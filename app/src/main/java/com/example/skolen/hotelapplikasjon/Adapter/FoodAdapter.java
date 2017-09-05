package com.example.skolen.hotelapplikasjon.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skolen.hotelapplikasjon.Model.Food;
import com.example.skolen.hotelapplikasjon.R;

import java.util.ArrayList;

/**
 * Created by Skolen on 08.05.2017.
 */

public class FoodAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Food> mFoods;

    private TypedArray images;

    public FoodAdapter(Context context, ArrayList<Food> food, TypedArray image) {
        this.mContext = context;
        this.mFoods = food;
        this.images = image;
    }

    @Override
    public int getCount() {
        return mFoods.size();
    }

    @Override
    public Object getItem(int position) {
        return mFoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView mImageView;
        TextView mTextName;
        TextView mTextCost;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.food_model, parent, false);
        }

        ViewHolder mViewHolder = new ViewHolder();

        mViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.food_image);
        mViewHolder.mTextName = (TextView) convertView.findViewById(R.id.food_name);
        mViewHolder.mTextCost = (TextView) convertView.findViewById(R.id.food_cost);

        Food food = mFoods.get(position);

        if (food != null) {
            mViewHolder.mImageView.setImageResource(images.getResourceId(position, position));
            mViewHolder.mTextName.setText(food.getName());
            mViewHolder.mTextCost.setText(food.getCost());
        }

        return convertView;
    }
}
