package getting_first_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.MainActivity;
import com.example.project.R;
import com.squareup.picasso.Picasso;

import main_application.MainApplicationActivity;

public class ProfileActivity extends AppCompatActivity {

    ImageView profilePictureImageView;

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

        profilePictureImageView = findViewById(R.id.profile_image);

        Bundle profilePhotoExtra = getIntent().getExtras();
        if (profilePhotoExtra != null){
            Uri uri = (Uri) profilePhotoExtra.get("PROFILE_PHOTO_URI");
            Picasso.get().load(uri).into(profilePictureImageView);
        }
        else Log.d("aabb uri", "null");




    }
}