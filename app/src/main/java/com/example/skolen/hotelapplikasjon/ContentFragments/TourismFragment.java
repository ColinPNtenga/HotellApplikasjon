package com.example.skolen.hotelapplikasjon.ContentFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.skolen.hotelapplikasjon.Adapter.TourismAdapter;
import com.example.skolen.hotelapplikasjon.Model.Tourism;
import com.example.skolen.hotelapplikasjon.GoogleMaps.LocationActivity;
import com.example.skolen.hotelapplikasjon.R;

import java.util.ArrayList;


public class TourismFragment extends Fragment {

    private String[] mName;
    private String[] mAddress;
    private String[] mDescription;

    private ListView mListView;

    private TypedArray mTypedArrayImages;

    private ProgressBar mProgressBar;

    private static final String Name = "com.example.skolen.tictactoe.name";


    public TourismFragment() {
        // Required empty public constructor
    }

    private void getArrayObjects() {
        mName = getActivity().getResources().getStringArray(R.array.attraction_names);
        mAddress = getActivity().getResources().getStringArray(R.array.attraction_address);
        mDescription = getActivity().getResources().getStringArray(R.array.attraction_description);
        mTypedArrayImages = getActivity().getResources().obtainTypedArray(R.array.attraction_images);

    }

    private ArrayList<Tourism> getTourists() {
        ArrayList<Tourism> touristAttractions = new ArrayList<>();
        touristAttractions.add(new Tourism(mName[0], mDescription[0], mAddress[0]));
        touristAttractions.add(new Tourism(mName[1], mDescription[1], mAddress[1]));
        touristAttractions.add(new Tourism(mName[2], mDescription[2], mAddress[2]));
        touristAttractions.add(new Tourism(mName[3], mDescription[3], mAddress[3]));
        touristAttractions.add(new Tourism(mName[4], mDescription[4], mAddress[4]));
        touristAttractions.add(new Tourism(mName[5], mDescription[5], mAddress[5]));
        touristAttractions.add(new Tourism(mName[6], mDescription[6], mAddress[6]));
        touristAttractions.add(new Tourism(mName[7], mDescription[7], mAddress[7]));
        touristAttractions.add(new Tourism(mName[8], mDescription[8], mAddress[8]));
        touristAttractions.add(new Tourism(mName[9], mDescription[9], mAddress[9]));

        return touristAttractions;
    }

    private class MyTask extends AsyncTask<Void, String, ArrayList<Tourism>> {

        ArrayList<Tourism> places = new ArrayList<>();

        int attractions;

        @Override
        protected void onPreExecute() {
            mProgressBar.setMax(9);
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(0, 122, 0)));

            attractions = 0;
        }

        @Override
        protected ArrayList<Tourism> doInBackground(Void... params) {
            try {
                places = getTourists();
                Thread.sleep(4000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return places;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            attractions++;
            mProgressBar.setProgress(attractions);
        }

        @Override
        protected void onPostExecute(ArrayList<Tourism> result) {
            super.onPostExecute(places);
            Log.e("Total", String.valueOf(places.size()));
            TourismAdapter tourismAdapter = new TourismAdapter(getActivity(), R.layout.tourist_adapter_view, places, mTypedArrayImages);
            mListView.setAdapter(tourismAdapter);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void getTouristAttraction() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int selectedAttraction = position;

                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Map Location");
                adb.setMessage("You have selected: " + mName[selectedAttraction]);
                adb.setNeutralButton("Show map", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getContext(), LocationActivity.class);
                        intent.putExtra(Name, mName[selectedAttraction]);
                        startActivity(intent);
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
        View view = inflater.inflate(R.layout.fragment_tourism, container, false);

        getArrayObjects();

        mListView = (ListView) view.findViewById(R.id.tourist_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_attractions);

        new MyTask().execute();

        getTouristAttraction();

        return view;
    }

}
