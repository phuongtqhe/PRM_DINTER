package com.example.Dinter.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.Dinter.R;
import com.example.Dinter.models.LanguageModel;
import com.example.Dinter.utils.SharedPref;

import java.util.List;

public class LanguageAdapter extends ArrayAdapter<LanguageModel> {
    SharedPref pref ;
    int savedLang;

    public LanguageAdapter(@NonNull Context context, @NonNull List<LanguageModel> dataArrayList) {
        super(context, R.layout.item_language_button, dataArrayList);
    }
    // Create a static class that holds the references to the views in your layout
    static private class ViewHolder {
        ImageView langImage;
        TextView langName;
        RadioButton langRdo;
        RelativeLayout buttonBound;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LanguageModel languageData = getItem(position);

        // Declare a ViewHolder object
        ViewHolder holder;

        // Check if the convertView is null
        if (convertView == null) {
            // If it is null, inflate a new view from the layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_language_button, parent, false);

            // Create a new ViewHolder object and assign the views to it
            holder = new ViewHolder();
            holder.langImage = convertView.findViewById(R.id.lang_flag);
            holder.langName = convertView.findViewById(R.id.lang_name);
            holder.langRdo = convertView.findViewById(R.id.lang_rdo);
            holder.buttonBound = convertView.findViewById(R.id.button_bound);

            // Set the holder object as a tag for the view
            convertView.setTag(holder);
        } else {
            // If it is not null, get the holder object from the tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the image and name for the views using the holder object
        assert languageData != null;
        holder.langImage.setImageResource(languageData.getFlagImage());
        holder.langName.setText(languageData.getLangName());

        pref= new SharedPref(this.getContext());
        savedLang = pref.readInteger("language", 0);
        if(position==savedLang) {
            holder.langRdo.setChecked(true);
            holder.langName.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.primary));
            holder.buttonBound.setBackgroundResource(R.drawable.selected_button_language_frame);
        }
        else {
            holder.langRdo.setChecked(false);
            holder.langName.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.secondary));
            holder.buttonBound.setBackgroundResource(R.drawable.button_language_frame);
        }

        // Return the view
        return convertView;
    }
}
