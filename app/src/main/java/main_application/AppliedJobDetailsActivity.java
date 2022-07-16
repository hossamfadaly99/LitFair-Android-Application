package main_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.project.R;
import com.stepstone.stepper.StepperLayout;

// tab_indicator_default
//question_number_indicator_selected

public class AppliedJobDetailsActivity extends AppCompatActivity {

    //private StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_job_details);



    }

    public void back(View view) {
        finish();
    }
}