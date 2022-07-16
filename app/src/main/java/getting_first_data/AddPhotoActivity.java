package getting_first_data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.ApiInterface;
import retrofit.UploadPhoto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.FileUtils;

public class AddPhotoActivity extends AppCompatActivity {

    String h = "Bearer " +
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTEsImlhdCI6MTY1NzM3MjQwMSwicm9sZSI6IlNlZWtlciIsImV4cCI6MTY1NzQ1ODgwMX0.WJ_5vwxzfT3Th_Hzb2JQdqaSiKSxWSP7jAzW0KYey9vbOrOlojRlffwHHXeGLTY_Ak3gVWcO1am8L-wPwTRA-R5l-2azvkQiiHMYYWfp-iWi1UEvkLri5eD4FmtmZm2rO7bYodxrvULSsZm1c2mXNdk5-io1GJT8udRtI2l5DYTNzDQdJVtPrr3IsG5cN04CuF8LMKPxcHbJfTSN9Gmaohmj5AqVl1fJipnVvLuIblOAc-jeWoiUQhIkJ7kc3KMH48CG27N5jTi6k4BRdOyySI_nLSD0oHfqOwxgE-5xPtGb4-x_dOXbh1tRoUITL81kiQf5vLxpzLwO0QXV06l4z1ra-E6CUvX9k0cccsHGdxtNU2QOEdoqxbRf6gyFeMnNdlMvF3V-y9BU8pcAzL6gxgB_JDZVRqwjN5pHr5apFWDUWFoSZw_5Rwh8PZXwj_RVaPVWGhemVSEPE0BtiDt3n3iDmeTyWpSxP7muTKZlq-VG-ZiS-bYk-3E51gAl7nGCpZSXqYyROTyM2TZnTcDKh5q-2bX1vGyVCj_H6pLGrdXMLV_QiJMZGMVVutuEj_YiDp0PMCX7cSUX1jZqpo0F1R-kfkfuo4T_QFWZ4rk9JAiSjx4XHIhIBnO-bzZTS_b--eBfrTDdpLwVhhO5JbvEJMZLmUVsjw0EbrVXrnGKoqI";
    Uri uri;
    private static final int MY_PERMISSION_REQUEST = 100;
    private final int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;

    ImageView circlePictureImageView, squarePictureImageView;
    FrameLayout pictureFrameLayout;

    Intent intent ;

    public void addPhoto(View view) {
        //send the image to the server
        uploadFile(uri);
    }

    public void skip(View view) {
        startActivity(new Intent(AddPhotoActivity.this, UploadResumeActivity.class));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        intent = new Intent(AddPhotoActivity.this, UploadResumeActivity.class);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST);
        }

        circlePictureImageView = (ImageView) findViewById(R.id.circle_picture_image_view);
        squarePictureImageView = (ImageView) findViewById(R.id.square_picture_image_view);
        pictureFrameLayout = findViewById(R.id.picture_frame_layout);

    }

    public void uploadPhotoClicked(View view) {
        Intent intent = new Intent();
        //show only images
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_FROM_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData();

            //send to profile
            intent.putExtra("PROFILE_PHOTO_URI",uri);

            //preview the image
            pictureFrameLayout.setPadding(0,0,0,0);
            Picasso.get().load(uri).into(circlePictureImageView);
            squarePictureImageView.setVisibility(View.GONE);
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

        File originalFile = FileUtils.getFile(this, fileUri);
        RequestBody filePart = RequestBody.create(
                MediaType.parse(getContentResolver().getType(fileUri)),originalFile);

        MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://litfair.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UploadPhoto uploadPhotoPost = new UploadPhoto();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<UploadPhoto> call = apiInterface.uploadPhoto(h,file);
        call.enqueue(new Callback<UploadPhoto>() {
            @Override
            public void onResponse(Call<UploadPhoto> call, Response<UploadPhoto> response) {
                Toast.makeText(getApplicationContext(), "Uploaded successfully",Toast.LENGTH_LONG).show();
                Intent i = new Intent(AddPhotoActivity.this, UploadResumeActivity.class);
                //i.putExtra("PROFILE_PHOTO_URI",uri);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), UploadResumeActivity.class).putExtra("PROFILE_PHOTO_URI",uri.toString()));
            }

            @Override
            public void onFailure(Call<UploadPhoto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("aaaa","error");
            }
        });

    }

}