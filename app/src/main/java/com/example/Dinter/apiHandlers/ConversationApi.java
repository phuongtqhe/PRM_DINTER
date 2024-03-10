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
    public void getAllConversation(ApiCallback apiCallback){
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        ConversationService conversationService = retrofit.create(ConversationService.class);
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
}
