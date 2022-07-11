package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import retrofit.LoginPost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText etUserName, etPassword;
    Button btnLogin;
    LinearLayout tvSignUp;
    ProgressBar progressBar;
    String msg;
    String tokenObject;
    Boolean status;
    public static boolean isLoggedIn;
    public static String token;
    Boolean passwordHidden;
//    SharedPreferences preferences;
//    SharedPreferences.Editor editor;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        tvSignUp = findViewById(R.id.tvRegister);
        //progressBar = findViewById(R.id.progress);

//         preferences = getSharedPreferences("sharedFile", MODE_PRIVATE);
//         editor = preferences.edit();


        //hide password
        passwordHidden = false;
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (passwordHidden == false){
                            passwordHidden = true;
                            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }else{
                            passwordHidden = false;
                            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }

                        return true;
                    }
                }
                return false;
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignUp.class);
                startActivity(i);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,password;
                email = etUserName.getText().toString();
                password = etPassword.getText().toString();
                View parentLayout = findViewById(android.R.id.content);

                hideKeyboard(Login.this);




                if(isValidEmail(email) && !password.equals("")  ){
                    sendToServer(email,password);
                }else if (email.equals("") || password.equals("") ){
                    Snackbar.make(parentLayout, "Enter all the fields please!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }else if (!isValidEmail(email)) {
                    Snackbar.make(parentLayout, "Enter a valid E-mail please!", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }

            }
        });

    }

    private void sendToServer(String email, String password){


        LoginPost loginPost = new LoginPost(email, password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://litfair.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface =retrofit.create(ApiInterface.class);
        //the post
        Call<LoginPost> call =apiInterface.getLogin(loginPost);

        call.enqueue(new Callback<LoginPost>() {
            @Override
            public void onResponse(Call<LoginPost> call, Response<LoginPost> response) {
                status = response.isSuccessful();
                tokenObject= "";
                String toastFail = "please enter a valid E-mail and password";
                if (status) {tokenObject = response.body().getTokenObject();}
                //else msg = response.body().getMsg();


                if (!(tokenObject.equals("")) ){

                    SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("loggedToken", tokenObject);
                    editor.apply();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else Toast.makeText(getApplicationContext(), toastFail,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<LoginPost> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No Internet Connection",Toast.LENGTH_LONG).show();
            }
        });



    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    /* Hide Keyboard */
    private void hideKeyboard(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = activity.getCurrentFocus();
        if(focus != null)
            inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }



}