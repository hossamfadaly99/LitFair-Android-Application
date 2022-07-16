package main_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainApplicationActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private AppliedFragment appliedFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_application);

        homeFragment = new HomeFragment();
        appliedFragment = new AppliedFragment();
        settingFragment = new SettingFragment();

        replace(homeFragment);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_page:
                        replace(homeFragment);
                        break;
                    case R.id.applied_page:
                        replace(appliedFragment);
                        break;
                    case R.id.setting_page:
                        replace(settingFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_frame, fragment);
        transaction.commit();
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container_frame, fragment);
        transaction.commit();
    }

}