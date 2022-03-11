package com.example.workoutapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.workoutapp.flashlight.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private flashlight fl;
    private accelerometer am;
    private Button btnOF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = new flashlight(this);
        //am = new accelerometer(this,fl); //This can test if motion detection connects with flashlight well
        btnOF = (Button) findViewById(R.id.btnOnOff);
        btnOF.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                //fl.blinkFlash(10);  //This enables a button for blinking 10 seconds

            }
        });

        final ListView list = findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Easy");
        arrayList.add("Medium");
        arrayList.add("Hard");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                   android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(MainActivity.this,clickedItem,Toast.LENGTH_LONG).show();
            }
        });
    }
}