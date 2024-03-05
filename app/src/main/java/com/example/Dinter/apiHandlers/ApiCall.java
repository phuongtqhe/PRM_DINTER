package com.example.Dinter.apiHandlers;

import android.util.Log;

import com.example.Dinter.models.HobbyModel;
import com.example.Dinter.services.HobbyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCall {
    // Create an OkHttpClient with the logging interceptor
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);;

    Gson gson = new GsonBuilder().create();
    // Create an OkHttpClient with the logging interceptor
    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging)
            // Other configurations (if any)
            .build();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3008/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build();

    //define function to call api here

    public void getAllHobbies(ApiCallback apiCallback){
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        HobbyService hobbyService = retrofit.create(HobbyService.class);
        hobbyService.getAllHobbies().enqueue(new Callback<HobbyModel.JsonResponse>() {
            @Override
            public void onResponse(Call<HobbyModel.JsonResponse> call, Response<HobbyModel.JsonResponse> response) {
                if (response.isSuccessful()) {
                    HobbyModel.JsonResponse hobbyJson = response.body();
                    apiCallback.onHobbyFullLoaded(hobbyJson.getData());
                } else {
                    Log.d("Fetch ne: " , String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<HobbyModel.JsonResponse> call, Throwable t) {
                Log.d("Fetch ne: " , String.valueOf(t.getMessage()));
            }

        });
    }

}
