package com.example.skolen.hotelapplikasjon.GoogleMaps;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skolen.hotelapplikasjon.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final String Name = "com.example.skolen.tictactoe.name";

    private Marker mMarker1;
    private Marker mMarker2;


    private Polyline mPolyLine;

    private Button mButtonLocate;
    private EditText mEditTextPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mMapFragment.getMapAsync(this);

        mButtonLocate = (Button) findViewById(R.id.button_location);
        mEditTextPlace = (EditText) findViewById(R.id.editTextPlace);
        Intent intent = getIntent();
        String name = intent.getStringExtra(Name);
        mEditTextPlace.setText(name);

        viewLocation();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        customerLocation();

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

    }

    private void customerLocation() {
        LatLng mLatLng = new LatLng(59.9161671,10.7574865);
        mMarker1 = mMap.addMarker(new MarkerOptions().position(mLatLng).title("Campus Fjerdingen"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
    }

    private void geoLocation() throws IOException {
        String place = mEditTextPlace.getText().toString();

        Geocoder gc = new Geocoder(this);

        List<Address> list = gc.getFromLocationName(place, 1);
        Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZoom(lat, lng, 13);
        setMarker(locality, lat, lng);
        showPlaceDetails();
    }

    private void viewLocation() {
        mButtonLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    geoLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng mLatLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(mLatLng, zoom);
        mMap.moveCamera(update);
}

    private void setMarker(String locality, double lat, double lng) {
        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .draggable(true)
                .position(new LatLng(lat, lng));

        if (mMarker1 == null) {
            customerLocation();
        }

        else if (mMarker2 == null) {
            mMarker2 = mMap.addMarker(options);
            CalculationByDistance(mMarker1.getPosition(), mMarker2.getPosition());
            drawLine();

        }
        else {
            removeEverything();
            mMarker2 = mMap.addMarker(options);
        }

        updateLocation();

    }

    private void drawLine() {
        PolylineOptions options = new PolylineOptions()
                .add(mMarker1.getPosition())
                .add(mMarker2.getPosition())
                .color(Color.BLUE)
                .width(3);

                mPolyLine = mMap.addPolyline(options);
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));

        Toast.makeText(this, "Radius Value " + valueResult + "  KM  " + kmInDec + " Meter " + meterInDec, Toast.LENGTH_LONG).show();
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    private void removeEverything() {
        mMarker2.remove();
        mMarker2 = null;
        mPolyLine.remove();
    }

    private void showPlaceDetails() {
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View view = getLayoutInflater().inflate(R.layout.place_info, null);

                TextView mTextLocality = (TextView) view.findViewById(R.id.tv_locality);
                TextView mTextLatitude = (TextView) view.findViewById(R.id.tv_lat);
                TextView mTextLongitude = (TextView) view.findViewById(R.id.tv_lng);

                LatLng mLatLng = marker.getPosition();

                mTextLocality.setText(mEditTextPlace.getText().toString());
                mTextLatitude.setText("Latitude: " + mLatLng.latitude);
                mTextLongitude.setText("Longitude: " + mLatLng.longitude);

                return view;
            }
        });
    }

    private void updateLocation() {
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                Geocoder gc = new Geocoder(LocationActivity.this);
                LatLng mLatLng = marker.getPosition();
                double lat = mLatLng.latitude;
                double lng = mLatLng.longitude;

                List<Address> list = null;
                try {
                    list = gc.getFromLocation(lat, lng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address add = list.get(0);
                marker.setTitle(add.getLocality());
                marker.showInfoWindow();

            }
        });
    }

}
