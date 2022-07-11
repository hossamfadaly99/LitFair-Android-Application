package main_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.project.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    RecyclerView searchRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchRecyclerView = findViewById(R.id.search_recycler_view);
        JobListAdapter adapter = new JobListAdapter();
        searchRecyclerView.setAdapter(adapter);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<String> jobTitleList = new ArrayList<>();
        jobTitleList.add("Android developer");
        jobTitleList.add("Front End developer");
        jobTitleList.add("ML dev");
        jobTitleList.add("Graphic Designer");
        jobTitleList.add("Back End developer");

        adapter.setList(jobTitleList);


    }

    public void back(View view){
        finish();
    }

}