package com.example.Dinter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Dinter.Account;
import com.example.Dinter.ApiService;
import com.example.Dinter.R;
import com.example.Dinter.RetrofitClient;
import com.example.Dinter.models.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(view -> {
            System.out.println(email.getText().toString() + " " + password.getText().toString());
            Account acc = new Account(email.getText().toString(), password.getText().toString());
            login(acc);
        });
    }

    private void login(Account account) {
        ApiService apiService = RetrofitClient.getApiService();

        Call<User.Login>  call = apiService.login(account);
        System.out.println(call.request().url().toString());
        call.enqueue(new Callback<User.Login>() {
            @Override
            public void onResponse(Call<User.Login> call, Response<User.Login> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(Login.this, "success " + response.body() , Toast.LENGTH_SHORT).show();
                    System.out.println("success " + response);
                    System.out.println("success " + response.body());
                } else {
                    String errMsg;
                    try {
                        System.out.println(response.errorBody().string());
                        errMsg = new JSONObject(response.errorBody().string()).getString("message"); // or whatever your message is
                        System.out.println("1 " + errMsg);
                    } catch (Exception e) {
                        errMsg = String.valueOf(response.code());
                        System.out.println("2 " + errMsg);
                    }
                    System.out.println(errMsg);
                }
            }

            @Override
            public void onFailure(Call<User.Login> call, Throwable t) {

                Toast.makeText(Login.this, "fail " + t , Toast.LENGTH_SHORT).show();
                System.out.println("fail " + t);
            }
        });
    }

}