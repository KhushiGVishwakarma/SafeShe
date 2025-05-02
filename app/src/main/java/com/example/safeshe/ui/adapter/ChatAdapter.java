package com.example.safeshe.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.safeshe.R;
import com.example.safeshe.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<ChatMessage> chatMessages;

    public ChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, timestampText;
        ImageView avatarImage;

        public ChatViewHolder(View view) {
            super(view);
            messageText = view.findViewById(R.id.messageText);
            timestampText = view.findViewById(R.id.timestampText);
            avatarImage = view.findViewById(R.id.avatarImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return chatMessages.get(position).isUser() ? 1 : 0;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType == 1 ? R.layout.item_chat_user : R.layout.item_chat_bot, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatMessage msg = chatMessages.get(position);
        holder.messageText.setText(msg.getMessage());
        holder.timestampText.setText(msg.getTimestamp());
        holder.avatarImage.setImageResource(msg.isUser() ? R.drawable.ic_user_avatar : R.drawable.ic_bot_avatar);
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }
}
