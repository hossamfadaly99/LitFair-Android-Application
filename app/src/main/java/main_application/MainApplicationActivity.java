package main_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.project.R;

import java.util.ArrayList;

public class MainApplicationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_application);

        recyclerView = findViewById(R.id.recycler);
        JobListAdapter adapter = new JobListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<String> jobTitleList = new ArrayList<>();
        jobTitleList.add("Android developer");
        jobTitleList.add("Front End developer");
        jobTitleList.add("ML dev");
        jobTitleList.add("Graphic Designer");
        jobTitleList.add("Back End developer");

        adapter.setList(jobTitleList);


        recyclerView = findViewById(R.id.recycler2);
        JobListAdapter adapter2 = new JobListAdapter();
        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter2.setList(jobTitleList);
    }
}