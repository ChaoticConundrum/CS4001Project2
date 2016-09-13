package com.example.cawate14.cs4001project2;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements Observer {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;

    private AccelerometerHandler accelerometerHandler = null;
    private LocationHandler locationHandler = null;

    private TextView x_axis = null;
    private TextView y_axis = null;
    private TextView z_axis = null;
    private TextView latlabel = null;
    private TextView lnglabel = null;
    private TextView latitude = null;
    private TextView longitude = null;

    int defaultTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x_axis = (TextView)findViewById(R.id.x);
        y_axis = (TextView)findViewById(R.id.y);
        z_axis = (TextView)findViewById(R.id.z);
        latlabel = (TextView)findViewById(R.id.latlabel);
        lnglabel = (TextView)findViewById(R.id.lnglabel);
        latitude = (TextView)findViewById(R.id.latitude);
        longitude = (TextView)findViewById(R.id.longitude);

        defaultTextColor = x_axis.getTextColors().getDefaultColor();

        // Setup accelerometer handler
        accelerometerHandler = new AccelerometerHandler(this);
        accelerometerHandler.addObserver(this);

        // Why is there not an enum for this?? String keys in code are evil.
        String perm = "android.permission.ACCESS_FINE_LOCATION";

        // Check for location permission
        int check = ContextCompat.checkSelfPermission(this, perm);
        if (check == PackageManager.PERMISSION_GRANTED) {
            // OK, setup location handler
            locationHandler = new LocationHandler(this);
            locationHandler.addObserver(this);
            locationHandler.getLastKnownLocation();
        } else {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{ perm }, PERMISSION_REQUEST_FINE_LOCATION);
            // Set error color on location fields
            latlabel.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.error));
            lnglabel.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.error));
            latitude.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.error));
            longitude.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.error));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST_FINE_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Setup location handler
                    locationHandler = new LocationHandler(this);
                    locationHandler.addObserver(this);
                    locationHandler.getLastKnownLocation();

                    // Reset color on location fields
                    latlabel.setTextColor(defaultTextColor);
                    lnglabel.setTextColor(defaultTextColor);
                    latitude.setTextColor(defaultTextColor);
                    longitude.setTextColor(defaultTextColor);
                }
                // Otherwise, just don't use location
                locationHandler = null;
                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable == accelerometerHandler) {
            // Update acceleration
            float[] xyz = (float[]) o;
            x_axis.setText(Float.toString(xyz[0]));
            y_axis.setText(Float.toString(xyz[1]));
            z_axis.setText(Float.toString(xyz[2]));
        } else if(observable == locationHandler){
            // Update location
            Location location = (Location) o;
            latitude.setText(Double.toString(((Location) o).getLatitude()));
            longitude.setText(Double.toString(((Location) o).getLongitude()));
        }
    }
}
