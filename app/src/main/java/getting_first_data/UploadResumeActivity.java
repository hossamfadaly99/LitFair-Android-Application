package getting_first_data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.ApiInterface;
import retrofit.UploadCvModel;
import retrofit.UploadPhoto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.FileUtils;

public class UploadResumeActivity extends AppCompatActivity {

    String h = "Bearer " +
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTEsImlhdCI6MTY1NzQ3MTY3OSwicm9sZSI6IlNlZWtlciIsImV4cCI6MTY1NzU1ODA3OX0.AOskfFm1Wb63KR6LxRUwxiTmYWC33Dfjw2M6WFWfZPRPSvg-v3a5Py-2UwjFVg47wskLsSU-EV8YpzuEz1_c7dHyiUYWytc9iNAJKr4r-jQRs0FGpM47LUt9edZmhqKp9V3qLm1x01BgGf_rL5Va2nURxRCdbpzU5fSWjlU8TB1W1iPJIApvTIIa2qEIy0ANUyQOVolx67NnkE67Aft8JWh8AwRmB6Y4hMCNfUvbPxWc8uoqjiqxKgBzRRh3DyYsxUz1Bi3olRrhZ5R2ptKg25jM-S49SuPlHaHw032vYJoCrdd0HnD-4va1YO3r0Hg1h5ORWjNi5u86rVnw_RLRrfl7V8dC5E4h4ObuoXoTAV0HZJr5juWWeE6OhZPOPGcS6mJREFJwLsCq3xhLVExLGNtLV3tqxCRwy0xCZCfMvOMj3HrZ9noYubRmFrD2CsZ8XdNo73yw4iz2duSAdqXh68ZAUS0I868Vq0PduJtFwvN1hw6stVLvolnXXfVhZrz9a6KfKLg88KxPKwd7ieGpcZs_knVMHAvjhVpDmWbx2o4CUoNioPFidDgp_5pMKN1vw9DiNRBwYWECikuPcMI1kMiDzeaHTwJIjEBn7cHPCKOgqIVlqvEN7Av4eYtnNu09XGJvrJwbyFFojwKcXcx0uuGi7ETsFZ02e0V8y2cY4Cc";
    Uri uri;
    private static final int MY_PERMISSION_REQUEST = 100;
    private int PICK_PDF_FROM_FILES_REQUEST = 2;

    Intent nextIntent;

    TextView selectResumeTextView;

    public void uploadResume(View view) {
        uploadFile(uri);

    }

    public void skip(View view) {
        startActivity(new Intent(UploadResumeActivity.this, ProfileActivity.class));
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resume);

        nextIntent = new Intent(UploadResumeActivity.this, ProfileActivity.class);

        //extraaaaaaaaaaa
        Bundle profilePhotoExtra = getIntent().getExtras();
        if (profilePhotoExtra != null){
            Uri uri = (Uri) profilePhotoExtra.get("PROFILE_PHOTO_URI");
            nextIntent.putExtra("PROFILE_PHOTO_URI",uri);
            Log.d("aaaa", uri.toString());
        }else Log.d("aaaa", "null");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST);
        }


        selectResumeTextView = findViewById(R.id.select_Resume_text_view);

        selectResumeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                //show only images
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select your resume"),
                        PICK_PDF_FROM_FILES_REQUEST);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_FROM_FILES_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();

            //preview the cv name
            String cvName = uri.getPath()
                    .replace("/document/raw:/storage/emulated/0/","");
            String cvPath = uri.getPath().replace("/document/raw:","");
            uri = Uri.parse(cvPath);
            selectResumeTextView.setText(cvName);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }else{

                }
                return;
            }
        }
    }

    private void uploadFile(Uri fileUri) {

        File originalFile = new File(fileUri.getPath());

        RequestBody filePart =
                RequestBody.create(MediaType.parse("multipart/form-data"),originalFile);

        MultipartBody.Part file = MultipartBody.Part.createFormData("file", originalFile.getName(), filePart);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://litfair.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<UploadCvModel> call = apiInterface.uploadCV(h, file);
        call.enqueue(new Callback<UploadCvModel>() {
            @Override
            public void onResponse(Call<UploadCvModel> call, Response<UploadCvModel> response) {
                Log.d("aaaa", response.body().getCode());
                if (response.body().getStatus().equals("success")) {
                    Toast.makeText(getApplicationContext(), response.body().getOriginalName() + " Uploaded successfully", Toast.LENGTH_LONG).show();
                    startActivity(nextIntent);
                }
            }

            @Override
            public void onFailure(Call<UploadCvModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("aaaa error", t.getMessage());
            }
        });

    }


}