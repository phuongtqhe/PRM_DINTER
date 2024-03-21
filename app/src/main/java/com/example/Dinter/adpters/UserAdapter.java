package com.example.Dinter.adpters;

import static com.example.Dinter.utils.Utils.convertBackslashToForward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.Dinter.R;
import com.example.Dinter.models.ConversationModel;
import com.example.Dinter.models.UserModel;
import com.example.Dinter.utils.Constants;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserModel> {

    public UserAdapter(@NonNull Context context, @NonNull List<UserModel> dataArrayList) {
        super(context, R.layout.item_user_layout, dataArrayList);
    }
    // Create a static class that holds the references to the views in your layout
    static private class ViewHolder {
        ShapeableImageView avatar;
        TextView username, conversationId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserModel userModel = getItem(position);

        // Declare a ViewHolder object
        UserAdapter.ViewHolder holder;

        // Check if the convertView is null
        if (convertView == null) {
            // If it is null, inflate a new view from the layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user_layout, parent, false);

            // Create a new ViewHolder object and assign the views to it
            holder = new UserAdapter.ViewHolder();
            holder.avatar = convertView.findViewById(R.id.avatar);
            holder.username = convertView.findViewById(R.id.username);
            holder.conversationId = convertView.findViewById(R.id.conversationId);
            // Set the holder object as a tag for the view
            convertView.setTag(holder);
        } else {
            // If it is not null, get the holder object from the tag
            holder = (UserAdapter.ViewHolder) convertView.getTag();
        }

        // Set the image and name for the views using the holder object
        assert userModel != null;
        holder.username.setText(userModel.getUsername());
        Picasso.get()
                .load(Constants.BACK_END_HOST + convertBackslashToForward(userModel.getAvatar()))
                .into(holder.avatar);

        // Return the view
        return convertView;
    }
}

