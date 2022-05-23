package retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("addUser")
    public Call<Post> storePost(@Body Post post);

    @POST("login")
    public Call<LoginPost> getLogin(@Body LoginPost loginPost);

    @GET("seeker/profile/info")
    Call<SeekerProfileInfo> getSeekerProfileInfo(@Header("Authorization") String header);
}
