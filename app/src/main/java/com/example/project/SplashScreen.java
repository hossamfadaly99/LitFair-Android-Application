package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Intro.IntroScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                checkLoggedIn();
                /*SharedPreferences preferences = getSharedPreferences("sharedFile", MODE_PRIVATE);
                Boolean isLoggedIn = preferences.getBoolean("is_logged_in", false);

                if (isLoggedIn){
                    Toast.makeText(SplashScreen.this, "Welcome back", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                else{
                startActivity(new Intent(SplashScreen.this, IntroScreen.class));
                }
                finish();*/
            }
        },3000);
    }

    private void checkLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String loggedToken = preferences.getString("loggedToken", "");

        if (loggedToken.equals("")){
            startActivity(new Intent(SplashScreen.this, IntroScreen.class));
            finish();
        }
        else{
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }
    }
}