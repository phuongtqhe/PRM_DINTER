package com.example.Dinter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Dinter.R;
import com.example.Dinter.adpters.MessageAdapter;
import com.example.Dinter.apiHandlers.ApiService;
import com.example.Dinter.apiHandlers.RetrofitClient;
import com.example.Dinter.models.Message;
import com.example.Dinter.models.MessageSocketIO;
import com.example.Dinter.models.UserModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoxChatActivity extends AppCompatActivity {
    private RecyclerView messageRecycle;
    private MessageAdapter messageAdapter;
    private List<Message> mListMessage;
    private EditText edtMessage;
    private ImageButton btnButton;
    private UserModel user;

    private TextView username;
    private String receipentId;
    ImageView back_icon;
    Socket mSocket = Login.mSocket;
    String conversationId;

    private UserModel currentUser = new UserModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_chat);
        Intent intent = getIntent();
        conversationId = intent.getStringExtra("conversationId");
        String usernameIntent = intent.getStringExtra("username");
        String imgAvatar = intent.getStringExtra("imgAvatar");
        // Replace with actual
        Log.d("dsa", conversationId);

        edtMessage = findViewById(R.id.edt_message);
        btnButton = findViewById(R.id.btn_send);
        messageRecycle = findViewById(R.id.rcv_message);
        SharedPreferences sharedPreferences = getSharedPreferences("dinter.txt",MODE_PRIVATE);
        String jsonUser = sharedPreferences.getString("user", null);
        Gson gson = new Gson();
        user  = gson.fromJson(jsonUser, UserModel.class);
        System.out.println("UserId: " + user.getId());
        username = findViewById(R.id.mesUsername);
        back_icon= findViewById(R.id.back_icon);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messageRecycle.setLayoutManager(linearLayoutManager);
        mListMessage = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        System.out.println(imgAvatar);
        String convertImg = imgAvatar.replace("\\","/");
        getMessage(convertImg);
        username.setText(usernameIntent);

        mSocket.on("getMessage", onNewMessage);
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        back_icon.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void sendMessage() {
        String message = edtMessage.getText().toString().trim();
        if(TextUtils.isEmpty(message)){
            return;
        }
        sendMessageToDB(conversationId, user.getId(), message);
        MessageSocketIO messagesSocketIO = new MessageSocketIO(conversationId, user.getId(), message, receipentId);
        Gson gson = new Gson();

        String newMessage = gson.toJson(messagesSocketIO);
        mSocket.emit("sendMessage", newMessage);
        mListMessage.add(new Message(message, user.getId(), message));
        messageAdapter.notifyDataSetChanged();
        messageRecycle.scrollToPosition(mListMessage.size() - 1);
        edtMessage.setText("");
    }
    private void sendMessageToDB(String conversationId, String senderId, String text){
        ApiService apiService = RetrofitClient.getApiService();
        Call<Message> call = apiService.sendMessage(new Message(conversationId, senderId, text));
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                System.out.println("Send message successfully");
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                System.out.println("Send message successfully failed!");
            }
        });
    }

    private void getMessage(String avatar){
        ApiService apiService = RetrofitClient.getApiService();
        System.out.println("conversationId =============== " + conversationId);
        Call<List<Message>> call = apiService.getMessage(conversationId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.isSuccessful()) {
                    mListMessage = response.body();
                    for(int i = 0; i<mListMessage.size(); i++){
                        if(mListMessage.get(i).getSenderId().equals(user.getId()) == false){
                            receipentId = mListMessage.get(i).getSenderId();
                            System.out.println("receipentId " + receipentId);
                            break;
                        }
                    }
                    Gson gson = new Gson();
                    System.out.println("");
                    System.out.println("list size" + mListMessage.size());
                    messageAdapter.setData(mListMessage, user.getId(), avatar);
                    messageRecycle.setAdapter(messageAdapter);
                    messageRecycle.scrollToPosition(mListMessage.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

//    private void getUserInfo(String id){
//        System.out.println("ID: " + id);
//        ApiService apiService = RetrofitClient.getApiService();
//        Call<UserModel.Login> call = apiService.getUserInfo(id);
//        call.enqueue(new Callback<UserModel.Login>() {
//            @Override
//            public void onResponse(Call<UserModel.Login> call, Response<UserModel.Login> response) {
//                currentUser = response.body();
//                System.out.println("currentId: " + currentUser.getUsername());
//            }
//
//            @Override
//            public void onFailure(Call<UserModel.Login> call, Throwable t) {
//
//            }
//        });
//    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("nhay vao day");
                    JSONObject data = (JSONObject) args[0];
                    String conversationId;
                    String recipientId;
                    String senderId;
                    String text;
                    try {
                        conversationId = data.getString("conversationId");
                        recipientId = data.getString("recipientId");
                        senderId = data.getString("senderId");
                        text = data.getString("text");
                    } catch (JSONException e) {
                        return;
                    }
                    // add the message to view
                    mListMessage.add(new Message(conversationId, senderId, text));
                    messageAdapter.notifyDataSetChanged();
                    messageRecycle.scrollToPosition(mListMessage.size() - 1);
                }
            });
        }
    };
}