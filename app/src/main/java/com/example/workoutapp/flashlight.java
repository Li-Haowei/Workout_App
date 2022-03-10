package com.example.workoutapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.text.Editable;

import androidx.annotation.RequiresApi;

public class flashlight {
    private boolean onOff;
    private boolean isFlashAvailable;
    private CameraManager mCameraManager;
    private String mCameraId;
    private Context context;
    public boolean getOnOff(){
        return onOff;
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
    public flashlight(Context context){
        this.context = context;
        this.onOff = false;
        this.mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        this.isFlashAvailable = context.getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }
}
