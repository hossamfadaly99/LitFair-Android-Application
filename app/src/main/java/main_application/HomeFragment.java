package main_application;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;

import java.util.ArrayList;

import data.JobsData;
import data.*;
import retrofit.ApiInterface;
import retrofit.SeekerProfileInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    View view;
    RecyclerView recommendedRecyclerView;
    RecyclerView recentRecyclerView;
    TextView searchTextView;
    String header = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTEsImlhdCI6MTY1Nzg5MzcxMCwicm9sZSI6IlNlZWtlciIsImV4cCI6MTY1Nzk4MDExMH0.Bdsjd0rTLCMBiROKsc3WZgu7C9N3_MXjkUulFdmNZAITOUdVgCYJ7qC8M_rpJuDqe9JzDof5_CpODLQar2G1yAa-hIv0_ljtW-4b0p1_Fkxb-UsFTLadbRKfu2DuUI75PEmO5QX_rbndHPjN-X0lTp9WN6bKOmCUYwwOPTRVyRamT29i0OfkyuZpY7CrOzK8O57IAcuNDOWKH9NsCr6pF72yIJFPYuHoBtxRq14TJ49Z1SzGHAV0y3CBcAd9x8Ux0Ew0pkm3TE4sYqrImnug2FbTrncA0JwacT1jlP-LXkKbd4-y93t_Ey7jVOXSeogJKjD8Nnafw34HtON4w9N4LiEpds7FZqgAMfXP4fKKqoGRf-v1hsKvOfm9xF6zY8TsqInrfy2lHO2R8IXLxhmTqL-2kXQkhJu0lVX_7cz3EZZaSFgZq8gZCVviAQLUZVtJGlo73ii5HptPzGLlHEsRHQTFBwpGZSPOffJCbGdq4ZOks9MUwn0_-8y8jfoeU8Zu4Hu4aHIn7RNn2p2uBAdrXY6m5HxBZ7ubaQ600uAlSSeIgFZldanT4NBR6r_xZxEYHHxdbtxKWqYdQh66apVMJEmtipebG2Bm_gDm9R-qFTuDq4LFKhBQPGpaZLU3WJDmAmBI_jbWTizxYztzUS6r_WQgdPbiE6UliCfgkEAykG0";
    ArrayList<CurrentData> jobTitleList;
    JobListAdapter recommendedAdapter;
    ProgressBar recommendedProgressBar,recentProgressBar;
    private JobListAdapter.RecyclerViewClickListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        searchTextView = view.findViewById(R.id.search_text_view);
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO open Search Fragment
            }
        });

        recommendedRecyclerView = view.findViewById(R.id.recommended_recycler_view);
        recentRecyclerView = view.findViewById(R.id.recent_recycler_view);

        recommendedProgressBar = view.findViewById(R.id.recommended_progressBar);
        recentProgressBar = view.findViewById(R.id.recent_progressBar);

        jobTitleList = new ArrayList<>();

        setOnClickListener();
        recommendedAdapter = new JobListAdapter(jobTitleList, listener);
        recommendedRecyclerView.setAdapter(recommendedAdapter);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        //TODO retrieve the data from te server
        sendToServer(header);

        recommendedAdapter.setList(jobTitleList);



        //recent jobs
        recentRecyclerView = view.findViewById(R.id.recent_recycler_view);
        //JobListAdapter recentAdapter = new JobListAdapter();
        recentRecyclerView.setAdapter(recommendedAdapter);
        recentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //recentAdapter.setList(jobTitleList);


        return view;
    }

    private void setOnClickListener() {
        listener = new JobListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                startActivity(new Intent(getActivity().getApplicationContext(), JobDetailsActivity.class));
            }
        };
    }


    private void sendToServer(String header) {
        recommendedProgressBar.setVisibility(View.VISIBLE);
        recentProgressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://litfair.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<JobsData> call = apiInterface.getAllJobs(header);

        call.enqueue(new Callback<JobsData>() {
            @Override
            public void onResponse(Call<JobsData> call, Response<JobsData> response) {
                if (response.isSuccessful() && response.body()!= null){

                    jobTitleList.addAll(response.body().getMsg().get(0).getCurrent_data());
                    //jobTitleList.addAll(response.body().getMsg().size())
                    Log.d("dddd", String.valueOf(response.body().getMsg().get(0).getCurrent_data().get(1)));
                    recommendedAdapter.notifyDataSetChanged();
                    recommendedProgressBar.setVisibility(View.GONE);
                    recentProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JobsData> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                recommendedProgressBar.setVisibility(View.GONE);
                recentProgressBar.setVisibility(View.GONE);
                Log.d("dddd",t.getMessage());
            }
        });

    }
}