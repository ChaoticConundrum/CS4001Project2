package com.example.cawate14.cs4001project2;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Observable;

public class LocationHandler extends Observable implements LocationListener {

    private LocationManager locationManager = null;

    public LocationHandler(Activity activity) {
        try {
            // Setup GPS manager
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            // This is the listener
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch(SecurityException e) {
            locationManager = null;
        }
    }

    public void getLastKnownLocation(){
        try {
            // Get the last known location to display immediately
            Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            setChanged();
            notifyObservers(lastKnown);
        } catch(SecurityException e) {
            locationManager = null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Notify observing avtivity
        setChanged();
        notifyObservers(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
