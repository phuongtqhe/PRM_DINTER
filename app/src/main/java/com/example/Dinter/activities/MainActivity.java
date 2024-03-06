package com.example.Dinter.activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.Dinter.BuildConfig;
import com.example.Dinter.R;
import com.example.Dinter.apiHandlers.ApiCall;
import com.example.Dinter.apiHandlers.ApiCallback;
import com.example.Dinter.apiHandlers.HobbyApi;
import com.example.Dinter.models.HobbyModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout drawerLayout, languageButton, infoButton, rightPart, blurSceneEffect;
    private MaterialToolbar materialToolbar;
    private ImageView closeDrawerButton;
    private HobbyApi apiCall;
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
        apiCall = new HobbyApi();

        //buttons
        materialToolbar = findViewById(R.id.header);
        languageButton = findViewById(R.id.language_drawer_btn);
        infoButton = findViewById(R.id.information_drawer_btn);

        //drawer navigator
        drawerLayout = findViewById(R.id.my_drawer_layout);
        closeDrawerButton = findViewById(R.id.close_drawer_btn);
        drawer_open_animator =  ObjectAnimator.ofFloat(drawerLayout, "translationX", 300f, 0f);
        drawer_open_animator.setDuration(1000); // 1000 milliseconds
        rightPart = findViewById(R.id.right_part);

        //blur
        blurSceneEffect = findViewById(R.id.blur_scene_effect);
    }
    private void initAction(){
        apiCall = new HobbyApi();
        apiCall.getAllHobbies(new ApiCallback() {
            @Override
            public void onHobbyFullLoaded(List<HobbyModel> hobbyList) {
                for (int i = 0; i < hobbyList.size(); i++) {
                    Log.d("HobbyItemNe", hobbyList.get(i).get_id());
                }
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
