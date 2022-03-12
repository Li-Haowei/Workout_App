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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;

import com.example.workoutapp.flashlight.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private flashlight fl;
    private accelerometer am;
    private Button btnEasy,btnMedian,btnHard;
    private ListView optionList;
    private ArrayList<Button> options = new ArrayList<Button>();
    private ArrayAdapter<Button> adapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = new flashlight(this);
        am = new accelerometer(this,fl);

        btnEasy = new Button(MainActivity.this);
        btnMedian = new Button(MainActivity.this);
        btnHard = new Button(MainActivity.this);
        btnEasy.setText("Easy");
        btnEasy.setTag("btnEasy");
        btnEasy.setTextSize(5);
        btnEasy.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL));
        btnMedian.setText("Median");
        btnMedian.setTag("btnMedian");
        btnMedian.setTextSize(5);
        btnMedian.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL));
        btnHard.setText("Hard");
        btnHard.setTag("btnHard");
        btnHard.setTextSize(5);
        btnHard.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL));


        btnEasy.setOnClickListener(view -> {
            fl.blinkFlash(1);
        });
        options.add(btnEasy);
        options.add(btnMedian);
        options.add(btnHard);
        optionList = findViewById(R.id.list);
        adapter = new ArrayAdapter<Button>(this, android.R.layout.simple_list_item_1, options);
        optionList.setAdapter(adapter);
    }

}