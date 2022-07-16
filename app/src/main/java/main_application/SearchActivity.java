package main_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.project.R;

import java.util.ArrayList;

import data.CurrentData;


public class SearchActivity extends AppCompatActivity {

    RecyclerView searchRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//
//        searchRecyclerView = findViewById(R.id.search_recycler_view);
//        JobListAdapter adapter = new JobListAdapter();
//        searchRecyclerView.setAdapter(adapter);
//        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//        ArrayList<CurrentData> jobTitleList = new ArrayList<>();
////        jobTitleList.add(new AllJobsCurrentData("Android developer","sd","fd","sd","fd"));
////        jobTitleList.add(new AllJobsCurrentData("ML dev","sd","fd","sd","fd"));
////        jobTitleList.add(new AllJobsCurrentData("Front End developer","sd","fd","sd","fd"));
////        jobTitleList.add(new AllJobsCurrentData("Graphic Designer","sd","fd","sd","fd"));
//
//
//        adapter.setList(jobTitleList);
//
//
    }

    public void back(View view){
        finish();
    }

}