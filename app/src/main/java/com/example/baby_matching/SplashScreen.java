package com.example.baby_matching;

import android.content.Intent;
import android.os.Bundle;
import java.lang.Thread;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread gotoMainScreen = new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) { }
            }
        };

        gotoMainScreen.start();
    }
}
