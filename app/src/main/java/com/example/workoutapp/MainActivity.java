package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {
    private flashlight fl;
    private accelerometer am;
    private Button btnStart,btnStop;
    private Boolean start;
    private TextView stepCount;
    private ListView optionList;
    private static final String[] items={"Easy", "Median", "Hard"};
    private int currentMode;
    private ArrayAdapter<String> adapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentMode = 0;
        fl = new flashlight(this);
        am = new accelerometer(this,fl);
        am.disableAccelerometerListening();
        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.stop);
        stepCount = findViewById(R.id.stepCount);
        start = false;
        final MediaPlayer mp1 = MediaPlayer.create(this, R.raw.superman);
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.chariotsoffire);
        mp2.seekTo(60000);
        final MediaPlayer mp3 = MediaPlayer.create(this, R.raw.rocky);
        optionList = findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        optionList.setAdapter(adapter);
        optionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                if(start==true){
                    Toast.makeText(MainActivity.this,R.string.haventStopped,Toast.LENGTH_SHORT).show();
                }else{
                    switch (i) {
                        case 0:
                            currentMode = 1;
                            Toast.makeText(MainActivity.this,R.string.easy,Toast.LENGTH_SHORT).show();
                            am.setStep(0);
                            am.setForce(70000);
                            stepCount.setText("Current Steps: " + am.getStep());
                            break;
                        case 1:
                            currentMode = 2;
                            Toast.makeText(MainActivity.this,R.string.median,Toast.LENGTH_SHORT).show();
                            am.setStep(0);
                            am.setForce(100000);
                            stepCount.setText("Current Steps: " + am.getStep());
                            break;
                        case 2:
                            currentMode = 3;
                            Toast.makeText(MainActivity.this,R.string.hard,Toast.LENGTH_SHORT).show();
                            am.setStep(0);
                            am.setForce(130000);
                            stepCount.setText("Current Steps: " + am.getStep());
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        btnStop.setEnabled(false);
        btnStart.setOnClickListener(view->{
            if(currentMode==0){
                Toast.makeText(MainActivity.this,R.string.noselectionwarning,Toast.LENGTH_SHORT).show();
            }
            else {
                if (start == false) {
                    btnStop.setEnabled(true);
                    start = true;
                    am.setStep(0);
                    stepCount.setText("Current Steps: " + am.getStep());
                    am.enableAccelerometerListening();
                    btnStart.setEnabled(false);
                }
            }
        });
        btnStop.setOnClickListener(view->{
            if(start==true){
                btnStart.setEnabled(true);
                start = false;
                currentMode = 0;
                am.setStep(0);
                am.enableAccelerometerListening();
                if(mp1.isPlaying()){
                    mp1.pause();
                    mp1.seekTo(0);
                }
                if(mp2.isPlaying()){
                    mp2.pause();
                    mp2.seekTo(60000);
                }
                if(mp3.isPlaying()){
                    mp3.pause();
                    mp3.seekTo(0);
                }
                btnStop.setEnabled(false);
            }else{
                Toast.makeText(MainActivity.this,R.string.haventstarted,Toast.LENGTH_SHORT).show();
            }
        });

        Handler handler =new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                handler.postDelayed(this, 1000);
                if(start){
                    stepCount.setText("Current Steps: "+am.getStep());
                    switch (currentMode){
                        case 1:
                            if (am.getStep() > 10) {
                                am.flashMode(true);
                            } else {
                                am.flashMode(false);
                            }
                            if (am.getStep() > 30) {
                                mp1.start();
                            }
                            break;
                        case 2:
                            if (am.getStep() > 30) {
                                am.flashMode(true);
                            } else {
                                am.flashMode(false);
                            }
                            if (am.getStep() > 45) {
                                mp2.start();
                            }
                            break;
                        case 3:
                            if (am.getStep() > 30) {
                                am.flashMode(true);
                            } else {
                                am.flashMode(false);
                            }
                            if (am.getStep() > 60) {
                                mp3.start();
                            }
                            break;
                    }
                }
            }
        };
        handler.postDelayed(r, 0000);
    }

}