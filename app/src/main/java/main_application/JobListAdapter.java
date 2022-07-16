package main_application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;

import data.CurrentData;
import data.JobsData;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder> {
    private ArrayList<CurrentData> jobTitleList = new ArrayList<>();
    private RecyclerViewClickListener listener;

    public JobListAdapter(ArrayList<CurrentData> jobTitleList, RecyclerViewClickListener listener) {
        this.jobTitleList = jobTitleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.jobTitleTextView.setText(jobTitleList.get(position).getTitle());
        holder.experienceTextView.setText(jobTitleList.get(position).getExperience());
        holder.jobTypeTextView.setText(jobTitleList.get(position).getJob_type());
        holder.locationTextView.setText(jobTitleList.get(position).getLocation());
        holder.companyNameTextView.setText(jobTitleList.get(position).getCompany_name());

    }

    @Override
    public int getItemCount() {
        return jobTitleList.size();
    }

    //TODO new
    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public void setList(ArrayList<CurrentData> jobTitleList){
        this.jobTitleList = jobTitleList;
        notifyDataSetChanged();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView jobTitleTextView,experienceTextView, jobTypeTextView, locationTextView, companyNameTextView;
        Button detailsButton;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.job_title);
            experienceTextView = itemView.findViewById(R.id.experience_textview);
            jobTypeTextView = itemView.findViewById(R.id.job_type_text_view);
            locationTextView = itemView.findViewById(R.id.company_location_textview);
            companyNameTextView = itemView.findViewById(R.id.company_name_textview);
            detailsButton = itemView.findViewById(R.id.details_button);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            JobListAdapter.this.listener.onClick(view, getAdapterPosition());
        }
    }

    private void addFragment(Fragment fragment) {
        MainApplicationActivity mainApplicationActivity = new MainApplicationActivity();
        FragmentTransaction transaction = mainApplicationActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_frame, fragment);
        transaction.commit();

    }
}
