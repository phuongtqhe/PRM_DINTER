package com.example.Dinter.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public class Utils {
    public static void changeLanguage(String langCode) {
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(langCode);
        // Call this on the main thread as it may require Activity.restart()
        AppCompatDelegate.setApplicationLocales(appLocale);
    }

    public static String convertBackslashToForward(String path) {
        return path.replace("\\", "/");
    }
}
