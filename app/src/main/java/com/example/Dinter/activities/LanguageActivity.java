package com.example.Dinter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Dinter.R;
import com.example.Dinter.adpters.LanguageAdapter;
import com.example.Dinter.databinding.ActivityLanguageBinding;
import com.example.Dinter.modelings.LanguageModel;
import com.example.Dinter.utils.Constants;
import com.example.Dinter.utils.SharedPref;
import com.example.Dinter.utils.Utils;

import java.util.List;

public class LanguageActivity extends AppCompatActivity {
    ActivityLanguageBinding langActivityBinding;
    List languageData;
    LanguageModel savedLanguageData;
    int savedLang;
    RelativeLayout btn_save, buttonBound, lastButtonBound;
    TextView langName, lastLangName;
    SharedPref pref;
    LanguageAdapter languageAdapter;
    RadioButton langRdo;
    RadioButton lastChosenRdo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        langActivityBinding = ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(langActivityBinding.getRoot());
        if(getSupportActionBar() != null) getSupportActionBar().hide();
        initDefine();
        initAction();
    }

    protected void initDefine(){
        languageData = LanguageModel.getAllLangData();
        languageAdapter = new LanguageAdapter(LanguageActivity.this, languageData);
        pref= new SharedPref(this);
        savedLang = pref.readInteger(Constants.LANGUAGE_STORE, 0);
        btn_save = findViewById(R.id.btnConfirmChange);
    }

    protected void goToNextActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }

    protected void initAction() {
        pref.saveBoolean(Constants.IS_FIRST_TIME_OPEN_APP_STORE, false);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedLanguageData = (LanguageModel) languageData.get(savedLang);
                Utils.changeLanguage(savedLanguageData.getLangCode());
                pref.saveInteger(Constants.LANGUAGE_STORE, savedLang);
                LanguageActivity.this.goToNextActivity();
            }
        });
        langActivityBinding.listview.setAdapter(languageAdapter);
        langActivityBinding.listview.setClickable(true);
        langActivityBinding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                langRdo = view.findViewById(R.id.lang_rdo);
                langName = view.findViewById(R.id.lang_name);
                buttonBound = view.findViewById(R.id.button_bound);
                if (lastChosenRdo != null) {
                    lastChosenRdo.setChecked(false);
                    lastLangName.setTextColor(getColor(R.color.secondary));
                    lastButtonBound.setBackgroundResource(R.drawable.button_language_frame);
                } else {
                    View crnt = langActivityBinding.listview.getChildAt(savedLang);
                    RadioButton rdo = crnt.findViewById(R.id.lang_rdo);
                    TextView tv = crnt.findViewById(R.id.lang_name);
                    RelativeLayout bb = crnt.findViewById(R.id.button_bound);
                    rdo.setChecked(false);
                    tv.setTextColor(getColor(R.color.secondary));
                    bb.setBackgroundResource(R.drawable.button_language_frame);
                }
                langName.setTextColor(getColor(R.color.primary));
                buttonBound.setBackgroundResource(R.drawable.selected_button_language_frame);
                langRdo.setChecked(true);
                savedLang = position;

                //save state
                lastChosenRdo = langRdo;
                lastButtonBound = buttonBound;
                lastLangName = langName;
            }
        });
    }
}
