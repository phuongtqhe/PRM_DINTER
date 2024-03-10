package com.example.Dinter.apiHandlers;

import com.example.Dinter.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/login")
    Call<UserModel.Login> login(@Body UserModel.Account account);

    @POST("user/register")
    Call<UserModel.Login> register(@Body UserModel.NewUser account);
}
