package getting_first_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.SignUp;

import retrofit.ApiInterface;
import retrofit.SeekerProfileInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationJobTitleActivity extends AppCompatActivity {

    EditText countryEditText,cityEditText;
    String h = "Bearer " +
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTEsImlhdCI6MTY1Njc1MTk3MSwicm9sZSI6IlNlZWtlciIsImV4cCI6MTY1NjgzODM3MX0.dX2NwfUnPXZXqKUtk9IYEzayt_pOvGtHrzqPO7n6gKN_HMH8AP9SpJzOkX_24EA23guuE-pKyyV3Zj_6tSY6zh6-LhENBEO87-uvTSndnZpYzn81CYf31Ew7CAJDXqYwznmbBXHiQLGQQjIQsVnt1MUgzf6v4ZctSGbxk6umsAGUsFwH_im-htSrupjugOYXBVaCluMI6pycsi76CYgvgtfN9Akx-cfZ1l2Ojx90XnHKEPnuD_Zi5AuZc4VDX4bmdKpt9nds7mSWUVj6rwNPYl1w96VWkrVRdU98PRVZL6I7tT22UdaveLb_EjGF9oWXFKbkxaPLnj2ocgttjQ1HSxQVx58sP_PGuneLYjbafcUCt-b1hm7ylqcx4iNZ_6CcQS_NPzpVsaZwtmIZGyD-Fc1_85IW_MVE9MLd_vtW4Nr9WwCTfx5Or3Y-cBkTzpuRYXSyOUEko7kPdBFBR4M5oTFzKipoQKJGIWjtcVn6RI7UCJH2Tqz062-5Twd-uIi9RcoWttYkAbavj5g4HVJeJP-0Q7JUwAUEZTD9Z1p88e-Zz9UlHl5Oj7gNQVJ3gBOklQPoQzFHri8Dv2eGILM1K4Tx6I5cb8yPq-CGLP5bnjqezXx_98b1rS1zIS5Zz2HjjKpQSHdhW9mpa6Zv3xbhcmNkDLoafzBMGgjH2TTHeDg";

    public void continueClicked(View view){
        sendLocationToServer(h);

    }

    private void sendToServer(String header) {

        SeekerProfileInfo post = new SeekerProfileInfo();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://litfair.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        //the post
        Call<SeekerProfileInfo> call = apiInterface.getSeekerProfileInfo(header);

        call.enqueue(new Callback<SeekerProfileInfo>() {
            @Override
            public void onResponse(Call<SeekerProfileInfo> call, Response<SeekerProfileInfo> response) {
                // response from the request
                //if (response.isSuccessful()) Toast.makeText(getApplicationContext(), "200 & 300",Toast.LENGTH_LONG).show();
                //else Toast.makeText(getApplicationContext(), "400 and up",Toast.LENGTH_LONG).show();

                Toast.makeText(LocationJobTitleActivity.this, response.body().getEmail(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<SeekerProfileInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void sendLocationToServer(String header) {
        String country = countryEditText.getText().toString();
        String city = cityEditText.getText().toString();
        SeekerProfileInfo post = new SeekerProfileInfo(country,city);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://litfair.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        //the post
        Call<SeekerProfileInfo> call = apiInterface.updateLocation(header,post);

        call.enqueue(new Callback<SeekerProfileInfo>() {
            @Override
            public void onResponse(Call<SeekerProfileInfo> call, Response<SeekerProfileInfo> response) {

                startActivity(new Intent(getApplicationContext(), AddPhotoActivity.class));

            }

            @Override
            public void onFailure(Call<SeekerProfileInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void trying(View view){
        sendToServer(h);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_job_title);

        countryEditText = findViewById(R.id.country_edit_text);
        cityEditText = findViewById(R.id.city_edit_text);



    }
}