package com.example.Dinter.activities;

import static com.example.Dinter.utils.Utils.convertBackslashToForward;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.Dinter.BuildConfig;
import com.example.Dinter.R;
import com.example.Dinter.adpters.ConversationAdapter;
import com.example.Dinter.adpters.LanguageAdapter;
import com.example.Dinter.adpters.UserAdapter;
import com.example.Dinter.apiHandlers.ApiCall;
import com.example.Dinter.apiHandlers.ApiCallback;
import com.example.Dinter.apiHandlers.ConversationApi;
import com.example.Dinter.apiHandlers.HobbyApi;
import com.example.Dinter.databinding.ActivityMainBinding;
import com.example.Dinter.models.ConversationModel;
import com.example.Dinter.models.HobbyModel;
import com.example.Dinter.models.UserModel;
import com.example.Dinter.utils.Constants;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout drawerLayout, languageButton, infoButton, rightPart, blurSceneEffect, exit_button;
    private MaterialToolbar materialToolbar;
    private ImageView closeDrawerButton;
    private ConversationApi apiCall;
    ConversationAdapter conversationAdapter;
    ShapeableImageView avatar;
    TextView username, email, dob_text, bio_text;
    ListView conversationListView;
    AutoCompleteTextView searchView;
    ImageView searchBtn;
    private int windowWidth;

    // Create a value animator that animates the width of the layout from 0 to WRAP_CONTENT
    ObjectAnimator drawer_open_animator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDefine();
        initAction();
    }

    private void initDefine(){
        //api call
        apiCall = new ConversationApi();

        //buttons
        materialToolbar = findViewById(R.id.header);
        languageButton = findViewById(R.id.language_drawer_btn);
        infoButton = findViewById(R.id.information_drawer_btn);
        searchView = findViewById(R.id.search_view);
        searchBtn = findViewById(R.id.search_btn);
        exit_button = findViewById(R.id.exit_button);

        //drawer navigator
        drawerLayout = findViewById(R.id.my_drawer_layout);
        closeDrawerButton = findViewById(R.id.close_drawer_btn);
        drawer_open_animator =  ObjectAnimator.ofFloat(drawerLayout, "translationX", 300f, 0f);
        drawer_open_animator.setDuration(1000); // 1000 milliseconds
        rightPart = findViewById(R.id.right_part);
        avatar= findViewById(R.id.avatar);
        username= findViewById(R.id.username);
        email=findViewById(R.id.email);
        dob_text=findViewById(R.id.dob_text);
        bio_text=findViewById(R.id.bio_text);

        //blur
        blurSceneEffect = findViewById(R.id.blur_scene_effect);

        //listview
        conversationListView = findViewById(R.id.listview);

    }
    private void initAction(){
        username.setText(UserModel.currentUser.getUsername());
        email.setText(UserModel.currentUser.getEmail());
        Log.d("fds", UserModel.currentUser.getEmail());
//        dob_text.setText(UserModel.currentUser.getDateOfBirth().toString());
        Picasso.get()
                .load(Constants.BACK_END_HOST + convertBackslashToForward(UserModel.currentUser.getAvatar()))
                .into(avatar);
        apiCall = new ConversationApi();
        apiCall.getAllConversation(new ApiCallback() {
            @Override
            public void onConversationFullLoaded(List<ConversationModel> conversationList) {
                ApiCallback.super.onConversationFullLoaded(conversationList);
                //new adapter for conversation
                conversationAdapter = new ConversationAdapter(MainActivity.this, conversationList);
                conversationListView.setAdapter(conversationAdapter);
            }
        });

        drawer_open_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int width = (int) animation.getAnimatedValue();
                drawerLayout.setTranslationX(-1 * width);
            }
        });
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            //open the drawer
            @Override
            public void onClick(View v) {
                //drawer actions
                drawer_open_animator =  ObjectAnimator.ofFloat(drawerLayout, "translationX", -1 * windowWidth, 0f);
                drawer_open_animator.start();
                blurSceneEffect.setVisibility(View.VISIBLE);
            }
        });

        closeDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the drawer
                drawer_open_animator =  ObjectAnimator.ofFloat(drawerLayout, "translationX", 0f, -1 * windowWidth);
                drawer_open_animator.start();
                blurSceneEffect.setVisibility(View.INVISIBLE);
            }
        });

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LanguageActivity.class));
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blurSceneEffect.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder (MainActivity.this); // this is the context
                builder.setTitle (getResources().getString(R.string.app_name)); // set the title
                builder.setMessage (getResources().getString(R.string.version_t) + ": " + BuildConfig.VERSION_NAME); // set the message
                builder.setNegativeButton (getString(R.string.cancel_t), new DialogInterface.OnClickListener () { // set the negative button
                    public void onClick (DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create ();
                dialog.show();
            }
        });

        exit_button.setOnClickListener(view -> {
            finishAffinity();
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of the SearchView
                if (searchView.getVisibility() == View.GONE) {
                    searchView.setVisibility(View.VISIBLE);
                } else {
                    searchView.setVisibility(View.GONE);
                }
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Code here will be triggered before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Code here will be triggered when the text is being changed.
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This is called after the text has been changed.
                String inputText = s.toString();
                if(inputText.equals("")){
                    conversationListView.setAdapter(conversationAdapter);
                } else{
                    apiCall.getFriendsOfUser(UserModel.currentUser.getId(), inputText, new ApiCallback() {
                        @Override
                        public void onUserListFullLoaded(List<UserModel> userList) {
                            if(userList.size() >0){
                                Log.d("dsa", userList.get(0).getUsername());
                                UserAdapter userAdapter = new UserAdapter(MainActivity.this, userList);
                                conversationListView.setAdapter(userAdapter);
                            }
                        }
                    });
                }
            }
        });

        rightPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the drawer
                drawer_open_animator =  ObjectAnimator.ofFloat(drawerLayout, "translationX", 0f, -1 * windowWidth);
                drawer_open_animator.start();
                blurSceneEffect.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setCurrentFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        windowWidth = drawerLayout.getWidth();
        drawer_open_animator =  ObjectAnimator.ofFloat(drawerLayout, "translationX", -1 * windowWidth, 0f);
        drawerLayout.setTranslationX(-1 * windowWidth);
    }

    @Override
    protected void onResume() {
        super.onResume();
        blurSceneEffect.setVisibility(View.INVISIBLE);
    }
}
