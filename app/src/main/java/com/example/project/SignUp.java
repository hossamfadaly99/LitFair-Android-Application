package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import retrofit.ApiInterface;
import retrofit.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignUp extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword;
    Button btnJobSeeker, btnCompany, btnSignUp;
    LinearLayout tvLogin;
    ProgressBar progressBar;
    String messageResponse;
    Boolean status;
    String role = "Seeker";
    Boolean eyeClicked, eyeClickedConfirm;
    //RequestQueue requestQueue;


    private void sendToServer(String email, String password, String fname, String lname) {

        Post post = new Post(email, password, fname, lname, "Seeker");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://litfair.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        //the post
        Call<Post> call = apiInterface.storePost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                // response from SignUp
                status = response.isSuccessful();
//                else Toast.makeText(getApplicationContext(), "400 and up",Toast.LENGTH_LONG).show();
                if (status) {
                    Toast.makeText(getApplicationContext(), "successful registration", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                    finish();

                } else
                    Snackbar.make(findViewById(android.R.id.content), "Account is already registered!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                /*if (status) {
//                else Toast.makeText(getApplicationContext(), "400 and up",Toast.LENGTH_LONG).show();

                    messageResponse = response.body().getMsg();
                    if (messageResponse.equals("success")) {
                        Toast.makeText(getApplicationContext(), "successful registration", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                } else Snackbar.make(findViewById(android.R.id.content), "Account is already registered!", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(getResources().getColor(android.R.color.holo_red_light))
                        .show();*/
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tvLogin = findViewById(R.id.tvLogin);
        //btnJobSeeker = findViewById(R.id.job_seeker_btn);
        //btnCompany = findViewById(R.id.Company_btn);
        etFirstName = findViewById(R.id.first_name_editText);
        etLastName = findViewById(R.id.last_name_editText);
        etEmail = findViewById(R.id.email_editText);
        etPassword = findViewById(R.id.password_editText);
        etConfirmPassword = findViewById(R.id.confirm_password_editText);
        btnSignUp = findViewById(R.id.signup_btn);
        eyeClicked = false;
        eyeClickedConfirm = false;

        //progressBar = findViewById(R.id.progress);

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (eyeClicked == false) {
                            eyeClicked = true;
                            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            //etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_on_eye,0);
                        } else {
                            eyeClicked = false;
                            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            //etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_off_eye,0);
                        }

                        return true;
                    }
                }
                return false;
            }
        });

        etConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etConfirmPassword.getRight() - etConfirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (eyeClickedConfirm == false) {
                            eyeClickedConfirm = true;
                            etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            eyeClickedConfirm = false;
                            etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }

                        return true;
                    }
                }
                return false;
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName, lastName, email, password, confirmPassword;
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();

                hideKeyboard(SignUp.this);

                View parentLayout = findViewById(android.R.id.content);

                if (isValidEmail(email) && password.equals(confirmPassword) && !password.equals("") && !firstName.equals("") && !lastName.equals("")) {
                    sendToServer(email, password, firstName, lastName);
                } else if (firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("")) {
                    Snackbar.make(parentLayout, "Enter all the fields please!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!isValidEmail(email)) {
                    Snackbar.make(parentLayout, "Enter a valid E-mail please!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!password.equals(confirmPassword)) {
                    Snackbar.make(parentLayout, "Confirm the password correctly please!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }


            }
        });


    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = activity.getCurrentFocus();
        if (focus != null)
            inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


}