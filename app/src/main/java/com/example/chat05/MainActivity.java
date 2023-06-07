package com.example.chat05;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button mButton;

    private ChatAdapter mAdapter;
    private List<Message> mMessages;

    private OpenAIService mOpenAIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edit_text_message);
        mButton = findViewById(R.id.image_button_send);
        mRecyclerView = findViewById(R.id.recycler_view_messages);

        mOpenAIService = new OpenAIService(this);

        mMessages = new ArrayList<>();
        mAdapter = new ChatAdapter(mMessages);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mButton.setOnClickListener(v -> {
            String message = mEditText.getText().toString().trim();

            if (!TextUtils.isEmpty(message)) {
                sendMessage(message);
                mEditText.setText("");
            }
        });

    }

    private void sendMessage(String message) {
        mMessages.add(new Message("Sender", message, new Date(), true));

        mAdapter.notifyItemInserted(mMessages.size() - 1);
        mRecyclerView.smoothScrollToPosition(mMessages.size() - 1);

        mOpenAIService.getAIResponse(message, new OpenAIService.AIServiceCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    mMessages.add(new Message("Bot", response, new Date(), false));
                    mAdapter.notifyItemInserted(mMessages.size() - 1);
                    mRecyclerView.smoothScrollToPosition(mMessages.size() - 1);
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> {
                    mMessages.add(new Message("Bot", errorMessage, new Date(), false));
                    mAdapter.notifyItemInserted(mMessages.size() - 1);
                    mRecyclerView.smoothScrollToPosition(mMessages.size() - 1);
                });
            }
        });
    }
}