package com.example.Dinter.modelings;

import com.example.Dinter.R;

import java.util.Arrays;
import java.util.List;

public class LanguageModel {
    private int flagImage;
    String langCode, langName;

    public LanguageModel(int flagImage, String langCode, String langName){
        this.flagImage = flagImage;
        this.langCode = langCode;
        this.langName = langName;
    }

    public int getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(int flagImage) {
        this.flagImage = flagImage;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getLangName() {
        return langName;
    }

    public static List<LanguageModel> getAllLangData() {
        return Arrays.asList(
                new LanguageModel(R.drawable.english_rounded_flag, "gb", "English"),
                new LanguageModel(R.drawable.hindi_rounded_flag, "hi", "Hindi"),
                new LanguageModel(R.drawable.portuguese_rounded_flag, "pt", "Portuguese"),
                new LanguageModel(R.drawable.french_rounded_flag, "fr", "French"),
                new LanguageModel(R.drawable.spanish_rounded_flag, "es", "Spanish")
        );
    }
}
