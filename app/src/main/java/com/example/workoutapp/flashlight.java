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
    private int flashDuration;
    public boolean getOnOff(){
        return onOff;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void turnOn(){
        onOff = true;
        update();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void turnOff(){
        onOff = false;
        update();
    }
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
    public boolean getIsFlashAvailable(){
        return isFlashAvailable;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void blinkFlash(){
        long blinkDelay =100; //Delay in ms
        flashDuration = 10;
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
    public void setFlashDuration(int duration){
        //the duration will be in seconds
        flashDuration = duration*10;
    }
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
