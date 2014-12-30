package com.tale.location.sample;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.tale.location.LocationHelper;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    private LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationHelper = new LocationHelper(this);
    }

    @Override protected void onResume() {
        super.onResume();
        if (!locationHelper.isLocationEnabled()) {
            locationHelper.openSettingsScreen();
        } else {
            locationHelper.startUpdate(new LocationListener() {
                                           @Override public void onLocationChanged(Location location) {
                                               makeUseOfNewLocation(location);
                                           }
                                       });
        }
    }

    @Override protected void onStop() {
        locationHelper.stopUpdate();
        super.onStop();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        locationHelper.release();
    }

    private void makeUseOfNewLocation(Location location) {
        if (location != null) {
            Log.d(TAG, String.format("Found location updated => lat: %f, lng: %f", location.getLatitude(), location.getLongitude()));
        }
    }

}
