package main_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.LongFunction;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.ApiInterface;
import retrofit.UploadVideo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.FileUtils;

public class InterviewActivity extends AppCompatActivity implements ImageAnalysis.Analyzer, View.OnClickListener {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    PreviewView previewView;
    private ImageCapture imageCapture;
    private VideoCapture videoCapture;
    private Button bRecord;
    private TextView questionTextView,q1,q2,q3,q4,q5;
    private int counter = 1;
    String h = "Bearer " +
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTEsImlhdCI6MTY1NzU2NTI5Mywicm9sZSI6IlNlZWtlciIsImV4cCI6MTY1NzY1MTY5M30.OFNCf9oNW0qDv2pSKDxCyViuLiU7T5QJTJBM7bhMuSfGSOFcUZ3UgChoyf4EZAIW4mlxa_eACrEBCRT3rA2oRkb6FE7OK_LRqhgHb4kD3M1XBqQP79UH7qm2d0yLnXI0rjmY8wfXzXRtZupUE_3nyC_vaHhoJs6HP_u4G_MuDzlBWpzhaL9k4vLItsrCXx1TWZJfKItKBW7Nyz66Y1md5kRlYOrHo0nlcEgUJSr86D06rq8_-JBwTx0tKQyFKD6gmHWvi4hB727NSoRO7pH3xGrdrCG8xpMNNE-WJuwF8aLNHtTSGws1bJdvso-LWOmch2vsvjvn03z1bRCHGLffa8ra8IuBJNBKtT36fMNT_sN0mLzmvNv4TnGiRdK_g0qnYqasnaoiT98eyUKluKiIYhE5fWWo9gBpuIqwMLGYwGwy6_HLC0q8rF8sdgH4MzHBRc2ZnZxFGFh6CFAug9Mp5l9qvu_AKyifEzoxNr3LtkiY3zO-nR8TghxZQ1vDSEhR2qQu2JH5eXlcvdfYNMUnShWd2r4efPFsl4eNcZLCbGe6WYayL1hG8Ag3T7TkUhV1DMWFFtRTqFvJvHuO-zJewNooekJFBFlIIXkkJhX98de-4VakH96DUE7Gg7dOGlqy-APj09twhtySv71eVI_MTARUC0le7ltkXJpS8YsvVWc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        //disable night mode
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        questionTextView = findViewById(R.id.question_textView);
        q1 = findViewById(R.id.question1);
        q2 = findViewById(R.id.question2);
        q3 = findViewById(R.id.question3);
        q4 = findViewById(R.id.question4);
        q5 = findViewById(R.id.question5);

        previewView = findViewById(R.id.preview_view);

        bRecord = findViewById(R.id.record_button);
        bRecord.setText("start recording"); // Set the initial text of the button

        requirePermission();

        bRecord.setOnClickListener(this);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());
    }

    Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    @SuppressLint("RestrictedApi")
    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.DEFAULT_FRONT_CAMERA.getLensFacing())
                .build();
        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Image capture use case
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        // Video capture use case
        videoCapture = new VideoCapture.Builder()
                .setVideoFrameRate(30)
                .build();

        // Image analysis use case
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(getExecutor(), this);

        //bind to lifecycle:
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture, videoCapture);
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        // image processing here for the current frame
        Log.d("TAG", "analyze: got the frame at: " + image.getImageInfo().getTimestamp());
        image.close();
    }

    //TODO next question
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.record_button) {
            if (bRecord.getText() == "start recording") {
                bRecord.setText("stop recording");
                recordVideo();
            } else {
                bRecord.setText("start recording");
                videoCapture.stopRecording();
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void recordVideo() {
        Log.d("aaaa", "recordVideoFun");
        if (videoCapture != null) {
            Log.d("aaaa", "not null");
            long timestamp = System.currentTimeMillis();

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");

            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Log.d("aaaa", "try");
                videoCapture.startRecording(
                        new VideoCapture.OutputFileOptions.Builder(
                                getContentResolver(),
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                contentValues
                        ).build(),
                        getExecutor(),
                        new VideoCapture.OnVideoSavedCallback() {
                            @Override
                            public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {

                                Log.d("aaaa uri", outputFileResults.getSavedUri().getPath());
                                uploadFile(outputFileResults.getSavedUri());
                                //delete the video int the next step, after making sure it's uploaded
                                //deleteVideo(outputFileResults.getSavedUri());
                                Toast.makeText(InterviewActivity.this, "Video has been saved successfully.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                                Toast.makeText(InterviewActivity.this, "Error saving video: " + message, Toast.LENGTH_SHORT).show();
                                Log.d("aaaa", message);
                            }
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void deleteVideo(Uri savedUri) {
        File originalFile = FileUtils.getFile(this, savedUri);
        originalFile.delete();
    }

    public void requirePermission(){
        ActivityCompat.requestPermissions(InterviewActivity.this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5);
    }


    private void uploadFile(Uri fileUri) {

        File originalFile = FileUtils.getFile(this, fileUri);

        Log.d("aaaa path", originalFile.getPath());

        RequestBody filePart =
                RequestBody.create(MediaType.parse("multipart/form-data"),originalFile);

        MultipartBody.Part file = MultipartBody.Part.createFormData("video", originalFile.getName(), filePart);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://litfair.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<UploadVideo> call = apiInterface.uploadVideo(h, file);
        call.enqueue(new Callback<UploadVideo>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<UploadVideo> call, Response<UploadVideo> response) {
                Log.d("aaaa", response.body().getCode());
                if (response.body().getStatus().equals("success")) {
                    Toast.makeText(getApplicationContext(), response.body().getVideoUrl() + " Uploaded successfully", Toast.LENGTH_LONG).show();
                    Log.d("aaaa link",response.body().getVideoUrl());
                    nextQuestion();
                }
            }

            @Override
            public void onFailure(Call<UploadVideo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("aaaa error", t.getMessage());
            }
        });

        //originalFile.delete();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void nextQuestion() {
        if (counter == 1){
            counter++;
            questionTextView.setText("Question 2: Talk about a time when you demonstrated leadership");
            q1.setTextColor(getColor(R.color.text_gray));
            q1.setBackgroundResource(R.drawable.question_number_indicator_default);

            q2.setTextColor(getColor(R.color.white));
            q2.setBackgroundResource(R.drawable.question_number_indicator_selected);
        }
        else if (counter == 2){
            counter++;
            questionTextView.setText("Question 3: Talk about a time when you where working with a team and faced a challenge. How did  you overcome the problem?");
            q2.setTextColor(getColor(R.color.text_gray));
            q2.setBackgroundResource(R.drawable.question_number_indicator_default);

            q3.setTextColor(getColor(R.color.white));
            q3.setBackgroundResource(R.drawable.question_number_indicator_selected);
        }
        else if (counter == 3){
            counter++;
            questionTextView.setText("Question 4: What is the one of your weeknesses and how do you plan to overcome it?");
            q3.setTextColor(getColor(R.color.text_gray));
            q3.setBackgroundResource(R.drawable.question_number_indicator_default);

            q4.setTextColor(getColor(R.color.white));
            q4.setBackgroundResource(R.drawable.question_number_indicator_selected);
        }

        else if (counter == 4){
            counter++;
            questionTextView.setText("Question 5: Why do you think you worth hiring?");
            q4.setTextColor(getColor(R.color.text_gray));
            q4.setBackgroundResource(R.drawable.question_number_indicator_default);

            q5.setTextColor(getColor(R.color.white));
            q5.setBackgroundResource(R.drawable.question_number_indicator_selected);
        }

        else if (counter == 5){
            //next activity

        }

    }

}