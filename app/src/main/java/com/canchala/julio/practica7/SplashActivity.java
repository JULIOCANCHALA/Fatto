package com.canchala.julio.practica7;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLAH_DELAY=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent i=new Intent().setClass(SplashActivity.this,Main.class);
                startActivity(i);
                finish();
            }
        };

        Timer timer=new Timer();
        timer.schedule(task,SPLAH_DELAY);
    }
}
