package com.example.skolen.hotelapplikasjon.Firebase;

import android.app.Application;

/**
 * Created by Skolen on 04.05.2017.
 */

public class HotelApplikasjon extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        com.firebase.client.Firebase.setAndroidContext(this);
    }
}
