package com.example.Dinter.apiHandlers;

import android.util.Log;

import com.example.Dinter.models.HobbyModel;
import com.example.Dinter.services.HobbyService;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HobbyApi extends ApiCall{
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
