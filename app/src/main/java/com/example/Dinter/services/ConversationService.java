package com.example.Dinter.services;

import com.example.Dinter.models.ConversationModel;
import com.example.Dinter.models.HobbyModel;
import com.example.Dinter.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConversationService {
    @GET("http://10.0.2.2:3008/api/v1/conversation/find-user-chats-prm/{id}")
    Call<List<ConversationModel>> getAllConversation(@Path("id") String id);

    @GET("http://10.0.2.2:3008/api/v1/user/findFriendo/{userId}/{keyWord}")
    Call<List<UserModel>> getFriendsOfUser(@Path("userId") String userId, @Path("keyWord") String keyWord);
}
