package com.varscon.apitester.Retrofit;

import com.varscon.apitester.Model.AuthResponse;
import com.varscon.apitester.Model.ContactResponse;
import com.varscon.apitester.Model.SaveResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users")
    Call<ContactResponse> getContactList(@Query("page") String page);

    @POST("users")
    @FormUrlEncoded
    Call<SaveResponse> saveContact(@Field("name") String Name,
                                   @Field("job") String Job);

    @POST("login")
    @FormUrlEncoded
    Call<AuthResponse> signIn(@Field("email") String Email,
                              @Field("password") String Password);

    @POST("register")
    @FormUrlEncoded
    Call<AuthResponse> register(@Field("email") String Email,
                                @Field("password") String Password);
}
