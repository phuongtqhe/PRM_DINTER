package com.example.Dinter.apiHandlers;

import android.util.Log;

import com.example.Dinter.models.ConversationModel;
import com.example.Dinter.models.UserModel;
import com.example.Dinter.services.ConversationService;

import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationApi extends ApiCall{
    ConversationService conversationService = retrofit.create(ConversationService.class);
    public void getAllConversation(ApiCallback apiCallback){
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        conversationService.getAllConversation(UserModel.currentUser.getId()).enqueue(new Callback<List<ConversationModel>>() {
            @Override
            public void onResponse(Call<List<ConversationModel>> call, Response<List<ConversationModel>> response) {
                if (response.isSuccessful()) {
                    List<ConversationModel> listConversation = response.body();
                    apiCallback.onConversationFullLoaded(listConversation);
                } else {
                    Log.d("Fetch ne: " , String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<ConversationModel>> call, Throwable t) {
                Log.d("Fetch ne: " , String.valueOf(t.getMessage()));
            }

        });
    }

    public void getFriendsOfUser(String userId, String keyWord, ApiCallback apiCallback){
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        conversationService.getFriendsOfUser(userId, keyWord).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    List<UserModel> listUser = response.body();
                    apiCallback.onUserListFullLoaded(listUser);
                } else {
                    Log.d("Fetch ne: " , String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Log.d("Fetch ne: " , String.valueOf(t.getMessage()));
            }

        });
    }

    public void getConversation(String firstId, String secondId, ApiCallback apiCallback){
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        conversationService.getConversation(firstId, secondId).enqueue(new Callback<ConversationModel>() {
            @Override
            public void onResponse(Call<ConversationModel> call, Response<ConversationModel> response) {
                if (response.isSuccessful()) {
                    ConversationModel listUser = response.body();
                    apiCallback.onConverFullLoaded(listUser);
                } else {
                    Log.d("Fetch ne: " , String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ConversationModel> call, Throwable t) {
                Log.d("Fetch ne: " , String.valueOf(t.getMessage()));
            }

        });
    }
}
