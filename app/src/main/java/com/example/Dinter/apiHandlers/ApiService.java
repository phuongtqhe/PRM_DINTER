package com.example.Dinter.apiHandlers;

import com.example.Dinter.models.Message;
import com.example.Dinter.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("user/login")
    Call<UserModel.Login> login(@Body UserModel.Account account);

    @POST("user/register")
    Call<UserModel.Login> register(@Body UserModel.NewUser account);

    @GET("message/get-messages/{id}")
    Call<List<Message>> getMessage(@Path("id") String id);

    @POST("message/create-message")
    Call<Message> sendMessage(@Body Message message);

    @GET("user/public-user-info/{id}")
    Call<UserModel.UserDetails> getUserDetails(@Path("id") String id);
}
