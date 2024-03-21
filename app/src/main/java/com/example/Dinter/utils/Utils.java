package com.example.Dinter.utils;

import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static void changeLanguage(String langCode) {
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(langCode);
        // Call this on the main thread as it may require Activity.restart()
        AppCompatDelegate.setApplicationLocales(appLocale);
    }

    public static String convertBackslashToForward(String path) {
        return path.replace("\\", "/");
    }

    public static String formatDateTime(String input) {
        LocalDateTime dateTime = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.parse(input, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        LocalDateTime now = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now(ZoneOffset.UTC);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Duration.between(dateTime, now).toHours() < 24) {
                return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
            } else {
                return dateTime.format(DateTimeFormatter.ofPattern("MMM dd"));
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String input = "2024-03-02T02:15:53.696+00:00";
        System.out.println(formatDateTime(input));
    }
}
