package com.example.Dinter.apiHandlers;

import com.example.Dinter.models.ConversationModel;
import com.example.Dinter.models.HobbyModel;

import java.util.List;

public interface ApiCallback {
    public default void onHobbyFullLoaded(List<HobbyModel> hobbyList) {

    }

    public default void onConversationFullLoaded(List<ConversationModel> conversationList) {

    }
}
