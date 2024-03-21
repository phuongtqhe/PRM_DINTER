package com.example.Dinter.adpters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Dinter.R;
import com.example.Dinter.models.Message;
import com.example.Dinter.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private List<Message> mListMessage;
    private static final int ITEM_LEFT = 1;
    private static final int ITEM_RIGHT  = 2;
    private String userId;
    private String avatar;
    public void setData(List<Message> list, String userId, String avatar){
        this.mListMessage = list;
        this.userId = userId;
        this.avatar = avatar;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(mListMessage.get(position).getSenderId().equals(userId))
            return 2;
        else
            return 1;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_LEFT:
                return new LeftChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left,parent,false));
            case ITEM_RIGHT:
                return new RightChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = mListMessage.get(position);
        if(message == null) {
            return;
        }
        if(holder.getItemViewType() == ITEM_LEFT){
            LeftChatViewHolder viewHolder = (LeftChatViewHolder) holder;
            viewHolder.contents.setText(message.getText());
//            System.out.println(Constants.BACK_END_HOST_IMG + avatar);
            Picasso.get()
                    .load(Constants.BACK_END_HOST_IMG + avatar)
                    .into(viewHolder.imgView);
        }else{
            RightChatViewHolder viewHolder = (RightChatViewHolder) holder;
            viewHolder.contents.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        if(mListMessage.size() != 0){
            return mListMessage.size();
        }
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class LeftChatViewHolder extends MessageViewHolder {

        public TextView contents;
        public ImageView imgView;

        public LeftChatViewHolder(View itemView) {
            super(itemView);
            contents = (TextView) itemView.findViewById(R.id.tv_messagel);
            imgView = itemView.findViewById(R.id.mesAvatar);
        }
    }

    public class RightChatViewHolder extends MessageViewHolder {

        public TextView contents;

        public RightChatViewHolder(View itemView) {
            super(itemView);
            contents = (TextView) itemView.findViewById(R.id.tv_messager);
        }
    }
}