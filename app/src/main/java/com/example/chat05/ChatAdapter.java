package com.example.chat05;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private final List<Message> mMessages;

    public ChatAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView mSenderTextView;
        private final TextView mMessageTextView;
        private final TextView mTimeTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mSenderTextView = itemView.findViewById(R.id.sent_ca);
            mMessageTextView = itemView.findViewById(R.id.message_ca);
            mTimeTextView = itemView.findViewById(R.id.time_ca);
        }

        public void bind(Message message) {
            mSenderTextView.setText(message.getSender());
            mMessageTextView.setText(message.getMessage());
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            mTimeTextView.setText(dateFormat.format(message.getTime()));
        }
    }
}