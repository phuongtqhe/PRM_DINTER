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
import com.example.Dinter.utils.Utils;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class ConversationAdapter extends ArrayAdapter<ConversationModel> {

    public ConversationAdapter(@NonNull Context context, @NonNull List<ConversationModel> dataArrayList) {
        super(context, R.layout.item_language_button, dataArrayList);
    }
    // Create a static class that holds the references to the views in your layout
    static private class ViewHolder {
        ShapeableImageView avatar;
        TextView username, newMessage , timeSent, conversationId, receiverId, imageAvatar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ConversationModel conversationModel = getItem(position);

        // Declare a ViewHolder object
        ConversationAdapter.ViewHolder holder;

        // Check if the convertView is null
        if (convertView == null) {
            // If it is null, inflate a new view from the layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_conversation_layout, parent, false);

            // Create a new ViewHolder object and assign the views to it
            holder = new ConversationAdapter.ViewHolder();
            holder.avatar = convertView.findViewById(R.id.avatar);
            holder.newMessage = convertView.findViewById(R.id.newMessage);
            holder.username = convertView.findViewById(R.id.username);
            holder.timeSent = convertView.findViewById(R.id.timeSent);
            holder.conversationId = convertView.findViewById(R.id.conversationId);
            holder.receiverId = convertView.findViewById(R.id.receiverId);
            holder.imageAvatar = convertView.findViewById(R.id.imageAvatar);
            // Set the holder object as a tag for the view
            convertView.setTag(holder);
        } else {
            // If it is not null, get the holder object from the tag
            holder = (ConversationAdapter.ViewHolder) convertView.getTag();
        }

        // Set the image and name for the views using the holder object
        assert conversationModel != null;

        if(Objects.equals(conversationModel.getMembers().get(1).get_id(), UserModel.currentUser.getId())){
            holder.username.setText(conversationModel.getMembers().get(0).getUsername());
        } else{
            holder.username.setText(conversationModel.getMembers().get(1).getUsername());
        }
        if(UserModel.currentUser.getId() == conversationModel.getNewMessage().getSenderId()){
            holder.newMessage.setText("me: " + conversationModel.getNewMessage().getMessage());
        } else{
            holder.newMessage.setText("to me: " + conversationModel.getNewMessage().getMessage());
        }
        UserModel receiverUser = new UserModel();
        System.out.println(conversationModel.getMembers().get(0).get_id());
        for (int i = 0; i < conversationModel.getMembers().size(); i++) {
            if(conversationModel.getMembers().get(i).get_id().equals(UserModel.currentUser.getId())==false){
                receiverUser = conversationModel.getMembers().get(i);
                break;
            }
        }
        holder.conversationId.setText(conversationModel.get_id());
        holder.receiverId.setText(receiverUser.get_id());
        holder.imageAvatar.setText(receiverUser.getAvatar());
        holder.timeSent.setText(Utils.formatDateTime(conversationModel.getUpdatedAt()));
        Picasso.get()
                .load(Constants.BACK_END_HOST_IMG + convertBackslashToForward(receiverUser.getAvatar()))
                .into(holder.avatar);

        // Return the view
        return convertView;
    }
}

