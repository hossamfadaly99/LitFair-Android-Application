package getting_first_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    public void continueClicked(View view){
        startActivity(new Intent(getApplicationContext(), AddPhotoActivity.class));
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

    public void trying(View view){
        sendToServer("Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzM2LCJpYXQiOjE2NTMxMTUzMDIsInJvbGUiOiJTZWVrZXIiLCJleHAiOjE2NTMyMDE3MDJ9.fPI210OKu-ilQ5qvHEuTqUc41HVwR-4A3d3LvIsb6gM1ajzhccIHS2zOGrxVJz0kyvgTRl_K7Z6qIjgfo6KEK_7syDJafzWEfVSFqoQ9811mt65izeFVVeiSTLHwzoNXo5ApWO7ezLFV31uBpSFVjqyzbFiH_RGmd8cAqw1V1i-tm7OAXsgCnv0Nz52bCVt-CX1j8u5-g-euSnhGqhcDOk9uEMkuf_LLDWQP4rwyETTnAaiBmmIBomrUlMGParLZoxtw3g_UXzrM532X0sts37nq-XehieoBhLkq83ueRND2me8-13EBd47KFBkPobmjjbmnQojqbssXniPRfmkYneA8u4LpFO5z7gpvGOnm7Yv7zLRWKRXZnhNxEharxA-FdoJULr2c3PD_T4hg-Q7gmITDnX0JyYfKr3hEiapxsb6O5r5cCS7FH1q-aKBx93QVoqlt7u2-gBGlgS0xYrrHgH5FUuzkw2w4ENY3Ws9HbVTWqX9EZ4kC9w88Dkv4b9ctpCVBrGtjZJnrL45GJqWxS9ElXGN4zk3mu9LxwWOPd7B8DKLkIG59cVBvxkyqw8I97J_OYcD_TYWyjEpGDsHQGkB0snLFTg7bhE8MiCKAqq-gcEC7l1aQ7xM7b2-7mdd3Ac-n8UwVQzBoLT4nFcxmVTc87ujiyD-1LQCwhNkPpoE");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_job_title);




    }
}