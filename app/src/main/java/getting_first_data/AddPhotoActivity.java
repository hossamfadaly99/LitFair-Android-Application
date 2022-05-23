package getting_first_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project.R;

public class AddPhotoActivity extends AppCompatActivity {

    public void addPhoto(View view){
        //sendToServer with header
        startActivity(new Intent(getApplicationContext(), UploadResumeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);



    }
}