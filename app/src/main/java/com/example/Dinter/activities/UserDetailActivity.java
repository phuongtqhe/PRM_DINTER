package com.example.Dinter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Dinter.R;
import com.example.Dinter.apiHandlers.ApiService;
import com.example.Dinter.apiHandlers.RetrofitClient;
import com.example.Dinter.models.HobbyModel;
import com.example.Dinter.models.UserModel;
import com.example.Dinter.utils.Constants;
import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        findViewById(R.id.backBtnUserDetails).setOnClickListener(v -> onBackPressed());

        getUserDetails(UserModel.currentUser.getId());

    }

    private void getUserDetails(String id) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<UserModel.UserDetails> call = apiService.getUserDetails(id);
        call.enqueue(new Callback<UserModel.UserDetails>() {
            @Override
            public void onResponse(Call<UserModel.UserDetails> call, Response<UserModel.UserDetails> response) {
                if (response.isSuccessful()) {
                    UserModel.UserDetails userDetails = response.body();

                    String avatarUrl = userDetails.getAvatar().replace("\\", "/");
                    String[] parts = avatarUrl.split("public/images/users/", 2);
                    String basePath = parts[0];
                    Log.d("basePath: ", basePath);
                    String fileName = parts[1];
                    try {
                        String encodedAvatarUrl = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");

                        String finalUrl = Constants.BACK_END_HOST + "public/images/users/" + encodedAvatarUrl;

                        Log.d("Avatar: ", finalUrl);

                        ImageView avatar = findViewById(R.id.avatarUserDetails);
                        Picasso.get().load(finalUrl).into(avatar);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    TextView name = findViewById(R.id.nameUserDetails);
                    name.setText(userDetails.getUsername());

                    TextView email = findViewById(R.id.emailUserDetails);
                    email.setText(userDetails.getEmail());

                    TextView bio = findViewById(R.id.bioUserDetails);
                    bio.setText(userDetails.getBio());

                    TextView dob = findViewById(R.id.dobUserDetails);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dob.setText(LocalDateTime.parse(userDetails.getDateOfBirth(), DateTimeFormatter.ISO_DATE_TIME).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }

                    FlexboxLayout flexboxLayout = findViewById(R.id.flexBox);

                    for (HobbyModel hobby : userDetails.getHobbies()) {
                        // Create a new CardView
                        CardView cardView = new CardView(UserDetailActivity.this);
                        CardView.LayoutParams cardParams = new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);
                        cardParams.setMargins(0, 0, 15, 10);
                        cardView.setLayoutParams(cardParams);
                        cardView.setRadius(10); // Set corner radius

                        TextView textView = new TextView(UserDetailActivity.this);
                        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        textView.setPadding(20, 10, 20, 10);
                        textView.setText(hobby.getHobbyName());
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                        textView.setTypeface(null, Typeface.ITALIC);

                        cardView.addView(textView);
                        flexboxLayout.addView(cardView);
                    }

                } else {
                    String errorMessage;
                    try {
                        errorMessage = new JSONObject(response.errorBody().string()).getString("message");
                        Toast.makeText(UserDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(UserDetailActivity.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel.UserDetails> call, Throwable t) {
                Toast.makeText(UserDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}