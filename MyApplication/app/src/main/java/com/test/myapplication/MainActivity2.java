package com.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    Button btn_ON;
    Button btn_OFF;
    Button btn_home;

    static List<Boiler> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_ON = findViewById(R.id.btn_ON);
        btn_OFF = findViewById(R.id.btn_OFF);
        btn_home = findViewById(R.id.btn_home);

        Intent intent = getIntent();
        String status1 =intent.getStringExtra("status");
        btn_ON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (status1.equals("ON")) {
                    Toast.makeText(getApplicationContext(),"이미 켜져 있습니다",Toast.LENGTH_SHORT).show();
                }
                else {
                    new URLInputStream().getPage("http://10.10.141.51:8080/boiler/boilerUpdate/ON");

                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn_OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status1.equals("OFF")) {
                    Toast.makeText(getApplicationContext(),"이미 꺼져 있습니다",Toast.LENGTH_SHORT).show();
                }
                else {
                    new URLInputStream().getPage("http://10.10.141.51:8080/boiler/boilerUpdate/OFF");

                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}