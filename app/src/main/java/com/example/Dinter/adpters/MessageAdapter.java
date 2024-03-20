package com.example.Dinter.adpters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Dinter.R;
import com.example.Dinter.models.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private List<Message> mListMessage;
    private static final int ITEM_LEFT = 1;
    private static final int ITEM_RIGHT  = 2;
    private String userId;
    public void setData(List<Message> list, String userId){
        this.mListMessage = list;
        this.userId = userId;
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
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_recycle_row, parent, false);
//        return new MessageViewHolder(view);
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

        public LeftChatViewHolder(View itemView) {
            super(itemView);
            contents = (TextView) itemView.findViewById(R.id.tv_messagel);
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