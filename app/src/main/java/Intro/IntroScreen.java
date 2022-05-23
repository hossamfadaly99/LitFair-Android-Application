package Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Login;
import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.SignUp;
import com.google.android.material.snackbar.Snackbar;

public class IntroScreen extends AppCompatActivity {

    TextView skipTV;
    TextView titleTV;
    TextView textTV;
    Button nextBTN;
    ImageView picImgV;
    ImageView tab1ImgV, tab2ImgV, tab3ImgV;
    //Snackbar snackbar;
    int pageNum = 0;
    int h1, h2, h3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //check for the first time the user open the app
        checkFirstTime();


        skipTV = findViewById(R.id.intro_skip_text_view);
        titleTV = findViewById(R.id.intro_title_textView);
        textTV = findViewById(R.id.intro_text_textView);
        nextBTN = findViewById(R.id.intro_next_button);
        picImgV = findViewById(R.id.intro_pic);
        tab1ImgV = findViewById(R.id.tab1);
        tab2ImgV = findViewById(R.id.tab2);
        tab3ImgV = findViewById(R.id.tab3);

        //get image height
        h1= picImgV.getDrawable().getIntrinsicHeight();


        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageNum==0){
                    pageNum=1;
                    titleTV.setText(R.string.intro2TitleText);
                    textTV.setText(R.string.intro2text);
                    picImgV.setImageResource(R.drawable.intro2pic);
                    //picImgV.setTop(R.id.constraintLayout);

                    //set image padding
                    h2 = picImgV.getDrawable().getIntrinsicHeight();
                    int topPadding = (int) (h1 * 0.45);
                    picImgV.setPadding(0, topPadding, 0, 0);
                    //add 3dots
                    tab1ImgV.setImageResource(R.drawable.tab_indicator_default);
                    tab2ImgV.setImageResource(R.drawable.tab_indicator_selected);

                }
                else if (pageNum==1){
                    pageNum=2;
                    titleTV.setText(R.string.intro3TitleText);
                    textTV.setText(R.string.intro3text);
                    picImgV.setImageResource(R.drawable.intro3pic);
                    skipTV.setVisibility(View.INVISIBLE);

                    //set image padding
                    h3 = picImgV.getDrawable().getIntrinsicHeight();
                    int topPadding = (int) (h1 * 0.2);
                    picImgV.setPadding(0, topPadding, 0, 0);

                    //add 3dots
                    tab2ImgV.setImageResource(R.drawable.tab_indicator_default);
                    tab3ImgV.setImageResource(R.drawable.tab_indicator_selected);



                }
                else if(pageNum==2){
                    pageNum=3;
                    Intent signUpIntent = new Intent(getApplicationContext(), SignUp.class);
                    startActivity(signUpIntent);
                    finish();
                }
            }
        });

        skipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum=3;
                Intent signUpIntent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(signUpIntent);
                finish();
            }
        });
    }

    private void checkFirstTime() {
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String firstTime = preferences.getString("FirstTimeInstall", "");

        if (firstTime.equals("yes")){
            Intent signUpIntent = new Intent(getApplicationContext(), Login.class);
            startActivity(signUpIntent);
            finish();
        }
        else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "yes");
            editor.apply();
        }
    }
}