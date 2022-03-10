package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.workoutapp.flashlight.*;

public class MainActivity extends AppCompatActivity {
    private flashlight fl;
    private Button btnOF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = new flashlight(this);
        btnOF = (Button) findViewById(R.id.btnOnOff);

        btnOF.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                fl.blinkFlash();
            }
        });
    }
}