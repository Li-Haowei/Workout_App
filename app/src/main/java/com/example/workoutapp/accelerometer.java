package com.example.workoutapp;

import com.example.workoutapp.flashlight.*;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;

public class accelerometer {
    private float lastX, lastY, lastZ;  //old coordinate positions from accelerometer, needed to calculate delta.
    private float acceleration; //this acceleration will be calculated later and used to compare to SIGNIFICANT_SHAKE
    private float currentAcceleration;
    private float lastAcceleration;
    private flashlight fl;
    private int SIGNIFICANT_SHAKE = 30000;
    private Context context;
    private DecimalFormat df;
    private boolean flashMode;
    private int step;
    // enable listening for accelerometer events
    public void enableAccelerometerListening() {
        // The Activity has a SensorManager Reference.
        // This is how we get the reference to the device's SensorManager.
        SensorManager sensorManager =
                (SensorManager) context.getSystemService(
                        Context.SENSOR_SERVICE);    //The last parm specifies the type of Sensor we want to monitor


        //Now that we have a Sensor Handle, let's start "listening" for movement (accelerometer).
        //3 parms, The Listener, Sensor Type (accelerometer), and Sampling Frequency.
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);   //don't set this too high, otw you will kill user's battery.
    }
    public void disableAccelerometerListening() {
        //Disabling Sensor Event Listener is two step process.
        //1. Retrieve SensorManager Reference from the activity.
        //2. call unregisterListener to stop listening for sensor events
        //THis will prevent interruptions of other Apps and save battery.
        // get the SensorManager
        SensorManager sensorManager =
                (SensorManager) context.getSystemService(
                        Context.SENSOR_SERVICE);

        // stop listening for accelerometer events
        sensorManager.unregisterListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    public final SensorEventListener sensorEventListener = new SensorEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onSensorChanged(SensorEvent event) {
            // get x, y, and z values for the SensorEvent
            //each time the event fires, we have access to three dimensions.
            //compares these values to previous values to determine how "fast"
            // the device was shaken.
            float x = event.values[0];   //obtaining the latest sensor data.
            float y = event.values[1];   //sort of ugly, but this is how data is captured.
            float z = event.values[2];
            // save previous acceleration value
            lastAcceleration = currentAcceleration;
            // calculate the current acceleration
            currentAcceleration = x * x + y * y + z * z;   //This is a simplified calculation, to be real we would need time and a square root.
            // calculate the change in acceleration        //Also simplified, but good enough to determine random shaking.
            acceleration = currentAcceleration *  (currentAcceleration - lastAcceleration);
            // if the acceleration is above a certain threshold
            if (acceleration > SIGNIFICANT_SHAKE) {
                step++;
                if(flashMode) fl.blinkFlash(1);
                //df.format(x-lastX);
                //df.format(y-lastY);
                //df.format(z-lastZ);
            }
            lastX = x;
            lastY = y;
            lastZ = z;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    public int getStep(){
        return step;
    }
    public void setStep(int i){
        step = i;
    }
    public void flashMode(boolean bool){
        flashMode = bool;
    }
    public accelerometer(Context context, flashlight fl){
        this.context = context;
        this.fl = fl;
        flashMode = false;
        step = 0;
        acceleration = 0.00f;                                         //Initializing Acceleration data.
        currentAcceleration = SensorManager.GRAVITY_EARTH;            //We live on Earth.
        lastAcceleration = SensorManager.GRAVITY_EARTH;               //Ctrl-Click to see where else we could use our phone.
        enableAccelerometerListening();
    }
}
