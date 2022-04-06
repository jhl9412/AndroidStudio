package com.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    String status1;
    TextView status;
    TextView temp;
    Button btn_control;
    ImageView refreshButton;

    static List<Boiler> data;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
        temp = findViewById(R.id.temp);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String page = "http://10.10.141.51:8080/boiler/boilerList";

                try {
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder stringBuilder = new StringBuilder();
                    if(conn != null){

                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");
                        conn.setUseCaches(false);
                        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){

                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                            while(true){
                                String line = bufferedReader.readLine();
                                if(line == null) break;
                                stringBuilder.append(line + "\n");
                            }
                            bufferedReader.close();
                        }
                        conn.disconnect();
                    }
                    Gson gson = new Gson();

                    Type type = new TypeToken<List<Boiler>>() {}.getType();
                    data = gson.fromJson(String.valueOf(stringBuilder),type);

                    int id = data.get(0).getId();
                    int temp = data.get(0).getTemp();
                    status1 = data.get(0).getStatus();

                    tv_println_status("보일러 상태 : " + status1);
                    tv_println_temp("보일러 온도 : " + temp);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } //End of run()

        }); //End of Thread
        thread.start();


        btn_control = findViewById(R.id.btn_control);

        btn_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("status",status1);
                startActivity(intent);
            }
        });
        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public void tv_println_status(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                status.setText(data);
            }
        });
    }
    public void tv_println_temp(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                temp.setText(data + "℃");
            }
        });
    }
}
