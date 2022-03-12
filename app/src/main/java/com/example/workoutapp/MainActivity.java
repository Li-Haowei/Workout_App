package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutapp.flashlight.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private flashlight fl;
    private accelerometer am;
    private Button btnStart,btnStop;
    private TextView stepCount;
    private ListView optionList;
    private static final String[] items={"Easy", "Median", "Hard"};
    private ArrayAdapter<String> adapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = new flashlight(this);
        am = new accelerometer(this,fl);
        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.stop);
        stepCount = findViewById(R.id.stepCount);

        optionList = findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        optionList.setAdapter(adapter);

        optionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                switch (i) {
                    case 0:
                        Toast.makeText(MainActivity.this,R.string.easy,Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this,R.string.median,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this,R.string.hard,Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

}