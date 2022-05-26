package getting_first_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project.MainActivity;
import com.example.project.R;

import main_application.MainApplicationActivity;

public class ProfileActivity extends AppCompatActivity {

    public void back(View view){
        finish();
    }

    public void nextClicked(View view){
        startActivity(new Intent(getApplicationContext(), MainApplicationActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


    }
}