package com.example.workoutapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class flashlight {
    private boolean onOff;
    private boolean isFlashAvailable;
    private CameraManager mCameraManager;
    private String mCameraId;
    private Context context;

    /*
    return the state of flash light see if it is on or off
    @return onOff state
     */
    public boolean getOnOff(){
        return onOff;
    }

    /*
    Since turn on involves with control of flash light thus
    it needs to denote that the annotated element should only be called on the given API level or higher.
    This method turns on the light
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void turnOn(){
        onOff = true;
        update();
    }

    /*
    Since turn off involves with control of flash light thus
    it needs to denote that the annotated element should only be called on the given API level or higher.
    This method turns off the light
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void turnOff(){
        onOff = false;
        update();
    }

    /*
    Since update involves with control of flash light thus
    it needs to denote that the annotated element should only be called on the given API level or higher.
    This method uses the state of onOff to decide if turns on or turns off the light
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void update() {
        if(onOff){
            try {
                mCameraManager.setTorchMode(mCameraId, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }else{
            try {

                mCameraManager.setTorchMode(mCameraId, false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    @return if flash light is available on the current using device
     */
    public boolean getIsFlashAvailable(){
        return isFlashAvailable;
    }

    /*
    Since blinkFlash involves with control of flash light thus
    it needs to denote that the annotated element should only be called on the given API level or higher.
    This method blinks the flashlight
    @param int for the seconds you want it to blink
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void blinkFlash(int duration){
        long blinkDelay =100; //Delay in ms
        int flashDuration = duration*10;
        for (int i = 0; i < flashDuration; i++) {
            onOff = !onOff;
            update();
            try {
                Thread.sleep(blinkDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void blinkFlash(float duration){
        long blinkDelay =100; //Delay in ms
        int flashDuration = (int)duration*10;
        for (int i = 0; i < flashDuration; i++) {
            onOff = !onOff;
            update();
            try {
                Thread.sleep(blinkDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void negate(){
        if(onOff){
            turnOff();
        }else{
            turnOn();
        }
    }

    /*
    This is the constructor method for the class
    @param the context for the class
     */
    public flashlight(Context context){
        this.context = context;
        this.onOff = false;
        this.mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        this.isFlashAvailable = context.getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }
}
