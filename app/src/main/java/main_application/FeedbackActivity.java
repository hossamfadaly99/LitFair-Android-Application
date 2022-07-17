package main_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.project.R;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class FeedbackActivity extends AppCompatActivity {

    RatingBar recommendHiringProgressBar;
    TextView recommendHiringTextView;
    CircularProgressIndicator nofillersProgressBar, structuredAnswersProgressBar, pausedProgressBar, speakingRateProgressBar, friendlyProgressBar, calmProgressBar, notStressedProgressBar, notAwkwardProgressBar, engagedProgressBar, focusedProgressBar, authenticProgressBar, excitedProgressBar, smiledProgressBar;
    TextView nofillersTextView, structuredAnswersTextView, pausedTextView, speakingRateTextView, friendlyTextView, calmTextView, notStressedTextView, notAwkwardTextView, engagedTextView, focusedTextView, authenticTextView, excitedTextView, smiledTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        bindView();

        recommendHiringProgressBar.setNumStars(10);
        recommendHiringProgressBar.setMax(100);
        recommendHiringProgressBar.setProgress(18);

        nofillersProgressBar.setShowAnimationBehavior(BaseProgressIndicator.SHOW_OUTWARD);
        nofillersProgressBar.setMax(100);
        nofillersProgressBar.setProgress(77);

    }

    private void bindView() {
        recommendHiringProgressBar = findViewById(R.id.recommend_hiring_progressBar);
        recommendHiringTextView = findViewById(R.id.recommend_hiring_textview);

        nofillersProgressBar = findViewById(R.id.nofillers_progressBar);

    }

    public void back(View view) {
        finish();
    }

}