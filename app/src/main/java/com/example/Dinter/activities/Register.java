package com.example.Dinter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Dinter.R;
import com.example.Dinter.apiHandlers.ApiService;
import com.example.Dinter.apiHandlers.RetrofitClient;
import com.example.Dinter.models.UserModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mUsername;
    private EditText mRePassword;
    private Button mRegisterBtn;
    private TextView mLoginFormBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = findViewById(R.id.username);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mRePassword = findViewById(R.id.rePassword);
        mRegisterBtn = findViewById(R.id.signUpBtn);
        mLoginFormBtn = findViewById(R.id.showLoginFormBtn);

        mLoginFormBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });

        mRegisterBtn.setOnClickListener(view -> register(new UserModel.NewUser(mUsername.getText().toString(),
                mEmail.getText().toString(),
                mPassword.getText().toString(),
                mRePassword.getText().toString())));

    }

    private void register(UserModel.NewUser newUser) {
        ApiService apiService = RetrofitClient.getApiService();

        Call<UserModel.Login> call = apiService.register(newUser);
        call.enqueue(new Callback<UserModel.Login>() {
            @Override
            public void onResponse(Call<UserModel.Login> call, Response<UserModel.Login> response) {
                if(response.isSuccessful()) {
                    UserModel.Login res = response.body(); // response data
                    Toast.makeText(Register.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    //Next Page
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                } else {
                    String errorMessage;
                    try {
                        errorMessage = new JSONObject(response.errorBody().string()).getString("message");
                        Toast.makeText(Register.this, errorMessage, Toast.LENGTH_SHORT).show();

                    } catch(Exception e) {
                        Toast.makeText(Register.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel.Login> call, Throwable t) {
                Toast.makeText(Register.this, "t " + call , Toast.LENGTH_SHORT).show();
            }
        });
    }
}