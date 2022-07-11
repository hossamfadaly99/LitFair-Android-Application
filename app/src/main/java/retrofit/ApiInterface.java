package retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiInterface {
    @POST("addUser")
    public Call<Post> storePost(@Body Post post);

    @POST("login")
    public Call<LoginPost> getLogin(@Body LoginPost loginPost);

    @GET("seeker/profile/info")
    Call<SeekerProfileInfo> getSeekerProfileInfo(@Header("Authorization") String header);

    @PUT("seeker/profile/update")
    Call<SeekerProfileInfo> updateLocation(
            @Header("Authorization") String Header,
            @Body SeekerProfileInfo seekerLocation
    );

    @Multipart
    @POST("upload-photo")
    Call<UploadPhoto> uploadPhoto(
            @Header("Authorization") String Header,
            @Part MultipartBody.Part photo
    );
    @Multipart
    @POST("upload-file")
    Call<UploadCvModel> uploadCV(
            @Header("Authorization") String Header,
            @Part MultipartBody.Part cv
    );
}   
