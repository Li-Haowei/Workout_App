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
    private TextView timer;
    private boolean flashMode;
    private ListView optionList;
    private static final String[] items={"Easy", "Median", "Hard"};
    private int currentMode;
    private ArrayAdapter<String> adapter;
    private int seconds;
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
        timer = findViewById(R.id.timer);
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
                switch (i) {
                    case 0:
                        currentMode = 1;
                        Toast.makeText(MainActivity.this,R.string.easy,Toast.LENGTH_SHORT).show();
                        am.setForce(70000);
                        break;
                    case 1:
                        currentMode = 2;
                        Toast.makeText(MainActivity.this,R.string.median,Toast.LENGTH_SHORT).show();
                        am.setForce(100000);
                        break;
                    case 2:
                        currentMode = 3;
                        Toast.makeText(MainActivity.this,R.string.hard,Toast.LENGTH_SHORT).show();
                        am.setForce(130000);
                        break;
                    default:
                         break;
                }
                am.setStep(0);
                btnStart.setEnabled(true);
                stepCount.setText("Current Steps: " + am.getStep());
                timer.setText("00:00");
            }
        });
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);
        btnStart.setOnClickListener(view->{
            if(currentMode==0){
                Toast.makeText(MainActivity.this,R.string.noselectionwarning,Toast.LENGTH_SHORT).show();
            }
            else {
                btnStop.setEnabled(true);
                start = true;
                am.setStep(0);
                stepCount.setText("Current Steps: " + am.getStep());
                am.enableAccelerometerListening();
                btnStart.setEnabled(false);
                optionList.setEnabled(false);
            }
        });
        btnStop.setOnClickListener(view->{
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
            optionList.setEnabled(true);
        });

        Handler handler =new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                handler.postDelayed(this, 1000);
                if(start){
                    seconds++;
                    timer.setText(secondsConverter(seconds));
                    stepCount.setText("Current Steps: "+am.getStep());
                    switch (currentMode){
                        case 1:
                            if (am.getStep() > 10) {
                                flashMode = true;
                            } else {
                                flashMode = false;
                            }
                            if (am.getStep() > 30) {
                                mp1.start();
                            }
                            break;
                        case 2:
                            if (am.getStep() > 30) {
                                flashMode = true;
                            } else {
                                flashMode = false;
                            }
                            if (am.getStep() > 45) {
                                mp2.start();
                            }
                            break;
                        case 3:
                            if (am.getStep() > 30) {
                                flashMode = true;
                            } else {
                                flashMode = false;
                            }
                            if (am.getStep() > 60) {
                                mp3.start();
                            }
                            break;
                    }
                    if(flashMode) {
                        for (int i = 0; i < 4; i++) {
                            fl.negate();
                        }
                    }
                    if (am.getStep() > 100) {
                        if(seconds<=120){
                            Toast.makeText(MainActivity.this,"You are a rockstar",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Great job, keep practicing to get faster.",Toast.LENGTH_SHORT).show();
                        }
                        stepCount.setText("Current Steps: "+ 100);
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
                        optionList.setEnabled(true);
                        flashMode=false;
                        seconds=0;
                    }
                }
            }
        };
        handler.postDelayed(r, 0000);
    }
    public String secondsConverter(int seconds){
        String secs = seconds%60+"";
        String mins = seconds/60+"";
        if(secs.length()==1) secs = "0"+secs;
        if(mins.length()==1) mins = "0"+mins;
        return mins+":"+secs;
    }

}