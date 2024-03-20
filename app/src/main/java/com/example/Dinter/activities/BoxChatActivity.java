package com.example.Dinter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

    Socket mSocket = Login.mSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_chat);
        edtMessage = findViewById(R.id.edt_message);
        btnButton = findViewById(R.id.btn_send);
        messageRecycle = findViewById(R.id.rcv_message);
        SharedPreferences sharedPreferences = getSharedPreferences("dinter.txt",MODE_PRIVATE);
        String jsonUser = sharedPreferences.getString("user", null);
        Gson gson = new Gson();
        user  = gson.fromJson(jsonUser, UserModel.class);
        System.out.println("UserId: " + user.getId());
        username = findViewById(R.id.mesUsername);
        username.setText(user.getUsername());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messageRecycle.setLayoutManager(linearLayoutManager);
        mListMessage = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        getMessage();
        mSocket.on("getMessage", onNewMessage);
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = edtMessage.getText().toString().trim();
        if(TextUtils.isEmpty(message)){
            return;
        }
        sendMessageToDB("65f9967cda4c90103f327627", user.getId(), message);
        MessageSocketIO messagesSocketIO = new MessageSocketIO("65f9967cda4c90103f327627", user.getId(), message, receipentId);
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

    private void getMessage(){
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Message>> call = apiService.getMessage("65f9967cda4c90103f327627");
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.isSuccessful()) {
                    mListMessage = response.body();
                    for(int i = 0; i<mListMessage.size(); i++){
                        if(mListMessage.get(i).getSenderId().equals(user.getId()) == false){
                            receipentId = mListMessage.get(i).getSenderId();
                            System.out.println(receipentId);
                            break;
                        }
                    }
                    System.out.println(mListMessage.size());
                    messageAdapter.setData(mListMessage, user.getId());
                    messageRecycle.setAdapter(messageAdapter);
                    messageRecycle.scrollToPosition(mListMessage.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

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