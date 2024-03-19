package com.example.Dinter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Dinter.R;
import com.example.Dinter.apiHandlers.ApiService;
import com.example.Dinter.apiHandlers.RetrofitClient;
import com.example.Dinter.models.UserModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginBtn;
    private TextView mRegisterFormBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.loginBtn);
        mRegisterFormBtn = findViewById(R.id.showRegisterFormBtn);

        mLoginBtn.setOnClickListener(view -> login(new UserModel.Account(mEmail.getText().toString(), mPassword.getText().toString())));
        mRegisterFormBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    private void login(UserModel.Account account) {
        ApiService apiService = RetrofitClient.getApiService();

        Call<UserModel.Login> call = apiService.login(account);
        call.enqueue(new Callback<UserModel.Login>() {
            @Override
            public void onResponse(Call<UserModel.Login> call, Response<UserModel.Login> response) {
                if(response.isSuccessful()) {
                    UserModel.Login res = response.body(); // response data
                    UserModel user = res.getData(); // user info

                    //save user
                    UserModel.currentUser = response.body().getData();

                    Toast.makeText(Login.this, "Welcome " + response.body().getData().getUsername(), Toast.LENGTH_SHORT).show();

                    // Save user info + access/refresh token into dinter.txt
                    SharedPreferences sharedPreferences = getSharedPreferences("dinter.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    Gson gson = new Gson();

                    editor.putString("user",gson.toJson(user));
                    editor.putString("accessToken", res.getAccessToken());
                    editor.putString("refreshToken", res.getRefreshToken());
                    editor.apply();

                    //End Save user info + access/refresh token into dinter.txt

                    //Next Page
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    String errorMessage;
                    try {
                        errorMessage = new JSONObject(response.errorBody().string()).getString("message");
                        Toast.makeText(Login.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch(Exception e) {
                        Toast.makeText(Login.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel.Login> call, Throwable t) {
                Toast.makeText(Login.this, "t " + call , Toast.LENGTH_SHORT).show();
            }
        });

    }
}