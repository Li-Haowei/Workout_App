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
        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.stop);
        stepCount = findViewById(R.id.stepCount);
        start = false;
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
                        break;
                    case 1:
                        currentMode = 2;
                        Toast.makeText(MainActivity.this,R.string.median,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        currentMode = 3;
                        Toast.makeText(MainActivity.this,R.string.hard,Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        btnStart.setOnClickListener(view->{
            if(currentMode==0){
                Toast.makeText(MainActivity.this,R.string.noselectionwarning,Toast.LENGTH_SHORT).show();
            }
            else {
                if (start == false) {
                    start = true;
                    am.setStep(0);
                    stepCount.setText("Current Steps: " + am.getStep());
                }
            }
        });
        btnStop.setOnClickListener(view->{
            if(start==true){
                start = false;
                currentMode = 0;
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
                }
            }
        };
        handler.postDelayed(r, 0000);
    }

}