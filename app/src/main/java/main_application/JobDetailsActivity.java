package main_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.project.R;

public class JobDetailsActivity extends AppCompatActivity {

    private Button applyButton;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details2);

        applyButton = findViewById(R.id.apply_button);

        applyDialog();

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.create().show();
            }
        });

    }

    private void applyDialog() {
        dialog = new AlertDialog.Builder(JobDetailsActivity.this);
        dialog.setTitle("Apply to LitFair");
        dialog.setMessage("Are you sure you want to apply to this job?");
        dialog.setPositiveButton("apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(JobDetailsActivity.this, "applied", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(JobDetailsActivity.this, "canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back(View view) {
        finish();
    }
}