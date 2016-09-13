package com.example.cawate14.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private AccelerometerHandler accelerometerHandler = null;

    private TextView x_axis = null;
    private TextView y_axis = null;
    private TextView z_axis = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x_axis = (TextView)findViewById(R.id.x);
        y_axis = (TextView)findViewById(R.id.y);
        z_axis = (TextView)findViewById(R.id.z);
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometerHandler = new AccelerometerHandler(this);
        accelerometerHandler.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        float[] xyz = (float[])o;
        x_axis.setText(Float.toString(xyz[0]));
        y_axis.setText(Float.toString(xyz[1]));
        z_axis.setText(Float.toString(xyz[2]));
    }
}
