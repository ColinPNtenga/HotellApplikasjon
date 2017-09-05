package com.example.skolen.hotelapplikasjon.ContentFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.skolen.hotelapplikasjon.Adapter.FoodAdapter;
import com.example.skolen.hotelapplikasjon.Firebase.Entities.Order;
import com.example.skolen.hotelapplikasjon.Model.Food;
import com.example.skolen.hotelapplikasjon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RestaurantFragment extends Fragment {

    private String[] mName;
    private String[] mCost;

    private TypedArray mTypedArrayImages;

    private GridView mGridView;

    private FirebaseAuth mFirebaseAuth;

    private DatabaseReference mDatabaseReference;


    public RestaurantFragment() {
        // Required empty public constructor
    }

    private void getArrayObjects() {
        mCost = getActivity().getResources().getStringArray(R.array.food_cost);
        mName = getActivity().getResources().getStringArray(R.array.food_names);
        mTypedArrayImages = getActivity().getResources().obtainTypedArray(R.array.food_images);
    }

    private ArrayList<Food> getFoods() {
        ArrayList<Food> foods = new ArrayList<>();
        foods.add(new Food(mName[0], mCost[0]));
        foods.add(new Food(mName[1], mCost[1]));
        foods.add(new Food(mName[2], mCost[2]));
        foods.add(new Food(mName[3], mCost[3]));
        foods.add(new Food(mName[4], mCost[4]));
        foods.add(new Food(mName[5], mCost[5]));
        foods.add(new Food(mName[6], mCost[6]));
        foods.add(new Food(mName[7], mCost[7]));

        return foods;
    }

    private void orderFood() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Food Order");
                adb.setMessage("Your ordering: " + mName[position]);

                Context mContext = view.getContext();
                LinearLayout mLinearLayout = new LinearLayout(mContext);
                mLinearLayout.setOrientation(LinearLayout.VERTICAL);

                final EditText food = new EditText(getActivity());
                food.setInputType(InputType.TYPE_CLASS_TEXT);
                food.setHint("Food Name");
                food.setText(mName[position]);
                mLinearLayout.addView(food);

                final EditText name = new EditText(getActivity());
                name.setInputType(InputType.TYPE_CLASS_TEXT);
                name.setHint("Customer Name");
                mLinearLayout.addView(name);

                final EditText room = new EditText(getActivity());
                room.setInputType(InputType.TYPE_CLASS_NUMBER);
                room.setHint("Room Number");
                mLinearLayout.addView(room);

                final EditText quantity = new EditText(getActivity());
                quantity.setInputType(InputType.TYPE_CLASS_NUMBER);
                quantity.setHint("Quantity");
                mLinearLayout.addView(quantity);

                adb.setView(mLinearLayout);

                final Order order = new Order();

                adb.setNeutralButton("Order Food", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();

                        String foodName = food.getText().toString();
                        String customerName = name.getText().toString();
                        String customerRoom = room.getText().toString();
                        int foodQuantity = Integer.parseInt(quantity.getText().toString());

                        if (user != null) {
                            order.setFoodName(foodName);
                            order.setCustomerName(customerName);
                            order.setCustomerRoom(customerRoom);
                            order.setQuantity(foodQuantity);

                            mDatabaseReference.child("Orders").child(user.getUid()).push().setValue(order);
                        }
                    }
                });

                adb.setNegativeButton("Cancel", null);
                adb.show();


            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        getArrayObjects();

        mGridView = (GridView) view.findViewById(R.id.food_GridView);
        FoodAdapter foodAdapter = new FoodAdapter(this.getContext(), getFoods(), mTypedArrayImages);

        mGridView.setAdapter(foodAdapter);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        orderFood();

        return view;
    }

}
