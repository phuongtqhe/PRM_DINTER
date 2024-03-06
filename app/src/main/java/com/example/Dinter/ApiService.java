package com.example.Dinter;


import com.example.Dinter.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/login")
    Call<User.Login> login(
            @Body Account account
            );

    @GET("user/public-user-info/659425a130ee4afa35551699")
    Call<User> test(

    );


}
