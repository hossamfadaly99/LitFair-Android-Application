package getting_first_data;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.project.R;

public class UploadResumeActivity extends AppCompatActivity {

    public void uploadResume(View view){
        //send the server with header
        startActivity(new Intent(getApplicationContext(), UploadedResumCheckActivity.class));
    }

    public void back(View view){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resume);




    }
}