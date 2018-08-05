package com.example.firstactivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    static ArrayList<String> blackList = BlackList.blackList;
    static boolean judge = BlackList.judge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Button button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(FirstActivity.this, UndoActivity.class);
                startActivity(intent2);
            }
        });
        Button button3 = (Button) findViewById(R.id.button_3);
        button3.setOnClickListener(this);
        Button button4 = (Button) findViewById(R.id.button_4);
        button4.setOnClickListener(this);
        button4.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_3:
                new NewAppKill(FirstActivity.this).execute();
                Button thisbutt = (Button) findViewById(R.id.button_3);
                Button show = (Button) findViewById(R.id.button_4);
                BlackList.judge = true;
                Toast.makeText(FirstActivity.this, "监测开始", Toast.LENGTH_SHORT).show();
                thisbutt.setText("刷新监视");
                show.setVisibility(View.VISIBLE);
                break;
            case R.id.button_4:
                BlackList.judge = false;
                Toast.makeText(FirstActivity.this, "监测结束", Toast.LENGTH_SHORT).show();
                Button thatbutt = (Button) findViewById(R.id.button_3);
                thatbutt.setText("开始监听");
                Button hide = (Button) findViewById(R.id.button_4);
                hide.setVisibility(View.INVISIBLE);
                break;
        }
    }
}