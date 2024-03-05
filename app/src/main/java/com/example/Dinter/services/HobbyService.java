package com.example.Dinter.services;

import com.example.Dinter.models.HobbyModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HobbyService {
    @GET("http://10.0.2.2:3008/api/v1/hobby/_")
    Call<HobbyModel.JsonResponse> getAllHobbies();
}
