package main_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.project.R;

import java.util.ArrayList;

public class MainApplicationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    TextView searchTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_application);

        searchTextView = findViewById(R.id.search_text_view);
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });

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


        recyclerView2 = findViewById(R.id.recycler2);
        JobListAdapter adapter2 = new JobListAdapter();
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter2.setList(jobTitleList);



    }
}