package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    public void logOut(View view){
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("loggedToken", "");
        editor.apply();

        Intent i = new Intent(getApplicationContext(),Login.class);
        startActivity(i);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mImageViewPager = (ViewPager) findViewById(R.id.pager);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
//        tabLayout.setupWithViewPager(mImageViewPager, true);
    }
}

