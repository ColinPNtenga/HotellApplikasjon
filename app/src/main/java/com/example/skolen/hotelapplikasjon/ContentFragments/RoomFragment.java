package com.example.skolen.hotelapplikasjon.ContentFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.skolen.hotelapplikasjon.Customer.BookingActivity;
import com.example.skolen.hotelapplikasjon.Model.Room;
import com.example.skolen.hotelapplikasjon.R;
import com.example.skolen.hotelapplikasjon.Adapter.RoomAdapter;

import java.util.ArrayList;
import java.util.List;


public class RoomFragment extends Fragment {

    private String[] roomType;
    private String[] roomPrice;
    private String[] roomDescription;

    private int[] roomNumber;

    private TypedArray mTypedArrayImages;

    private ListView mListView;

    private RoomAdapter mRoomAdapter;

    public RoomFragment() {
        // Required empty public constructor
    }

    private void getArrayObjects() {
        roomType = getActivity().getResources().getStringArray(R.array.room_types);
        roomNumber = getActivity().getResources().getIntArray(R.array.room_numbers);
        roomPrice = getActivity().getResources().getStringArray(R.array.room_prices);
        mTypedArrayImages = getActivity().getResources().obtainTypedArray(R.array.room_images);
        roomDescription = getActivity().getResources().getStringArray(R.array.room_descriptions);
    }

    private ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room(roomType[0], roomNumber[0], roomPrice[0], roomDescription[0]));
        rooms.add(new Room(roomType[1], roomNumber[1], roomPrice[1], roomDescription[1]));
        rooms.add(new Room(roomType[2], roomNumber[2], roomPrice[2], roomDescription[2]));
        rooms.add(new Room(roomType[3], roomNumber[3], roomPrice[3], roomDescription[3]));
        rooms.add(new Room(roomType[4], roomNumber[4], roomPrice[4], roomDescription[4]));
        rooms.add(new Room(roomType[5], roomNumber[5], roomPrice[5], roomDescription[5]));

        return rooms;
    }

    private void bookRoom() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Hotel Booking");
                adb.setMessage("You have selected: " + roomType[position]);
                adb.setNeutralButton("Book room", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeBookedRoom();
                        Intent intent = new Intent(getContext(), BookingActivity.class);
                        intent.putExtra("ROOM", roomNumber[position]);
                        startActivity(intent);
                    }
                });

                adb.setNegativeButton("Cancel", null);
                adb.show();

            }
        });
    }

    private void removeBookedRoom() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = mRoomAdapter.getItem(position);
                mRoomAdapter.remove((Room) obj);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_room, container, false);
        getArrayObjects();

        mListView = (ListView) view.findViewById(R.id.room_view);
        mRoomAdapter = new RoomAdapter(getActivity(), R.layout.room_adapter_view, getRooms(), mTypedArrayImages);

        mListView.setAdapter(mRoomAdapter);

        bookRoom();

        return view;
    }
}
