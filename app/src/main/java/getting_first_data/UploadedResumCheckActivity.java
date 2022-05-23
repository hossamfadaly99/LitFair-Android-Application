package getting_first_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project.R;

public class UploadedResumCheckActivity extends AppCompatActivity {

    public void back(View view){
        finish();
    }

    public void continueClicked(View view){
        //send the server with header
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_resum_check);
    }
}