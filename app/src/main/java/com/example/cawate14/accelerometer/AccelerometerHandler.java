package com.example.cawate14.accelerometer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Observable;

public class AccelerometerHandler extends Observable implements SensorEventListener {

    private SensorManager sensorManager = null;
    private Sensor accelerometer = null;

    public AccelerometerHandler(Activity activity) {
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Check which sensor changed
        if(sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        // Notify observing activity
        setChanged();
        notifyObservers(sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
