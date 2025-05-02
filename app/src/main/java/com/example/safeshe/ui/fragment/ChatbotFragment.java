package com.example.safeshe.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safeshe.R;
import com.example.safeshe.chatbot.ChatBotResponse;
import com.example.safeshe.databinding.FragmentChatbotBinding;
import com.example.safeshe.model.ChatMessage;
import com.example.safeshe.ui.adapter.ChatAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatbotFragment extends Fragment {

    private static final int VOICE_INPUT_REQUEST_CODE = 100;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> chatMessages;
    private EditText inputMessage;
    private ImageButton sendButton, voiceButton;
    private TextToSpeech textToSpeech;
    private FragmentChatbotBinding binding;

    public ChatbotFragment() {}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatbotBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.header.toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            binding.header.collapsingToolbar.setTitle(getString(R.string.activity_chatbot_title));
            binding.header.collapsingToolbar.setSubtitle(getString(R.string.activity_chatbot_desc));
        }

        chatRecyclerView = view.findViewById(R.id.recyclerViewChat);
        inputMessage = view.findViewById(R.id.messageInput);
        voiceButton = view.findViewById(R.id.voiceButton);
        sendButton = view.findViewById(R.id.sendButton);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatRecyclerView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(v -> {
            String message = inputMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                inputMessage.setText("");
            }
        });

        voiceButton.setOnClickListener(v -> startVoiceInput());

        textToSpeech = new TextToSpeech(getContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.getDefault());
            }
        });

        setupSwipeActions();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendMessage(String message) {
        String timestamp = getCurrentTime();
        chatMessages.add(new ChatMessage(message, true, timestamp));
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        chatRecyclerView.scrollToPosition(chatMessages.size() - 1);

        new Handler().postDelayed(() -> {
            chatMessages.add(new ChatMessage("...", false, getCurrentTime()));
            chatAdapter.notifyItemInserted(chatMessages.size() - 1);
            chatRecyclerView.scrollToPosition(chatMessages.size() - 1);

            new Handler().postDelayed(() -> {
                chatMessages.remove(chatMessages.size() - 1);
                chatAdapter.notifyItemRemoved(chatMessages.size());

                String response = ChatBotResponse.getResponse(message);
                String responseTime = getCurrentTime();
                chatMessages.add(new ChatMessage(response, false, responseTime));
                chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                chatRecyclerView.scrollToPosition(chatMessages.size() - 1);

                speakOut(response);
            }, 1000);
        }, 500);
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            startActivityForResult(intent, VOICE_INPUT_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Voice input not supported!", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_INPUT_REQUEST_CODE && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                String voiceInput = results.get(0);
                inputMessage.setText(voiceInput);
                sendMessage(voiceInput);
            }
        }
    }

    private void speakOut(String message) {
        if (textToSpeech != null && !message.isEmpty()) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private void setupSwipeActions() {
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                ChatMessage msg = chatMessages.get(pos);

                if (direction == ItemTouchHelper.LEFT) {
                    chatMessages.remove(pos);
                    chatAdapter.notifyItemRemoved(pos);
                    Toast.makeText(getContext(), "Message deleted", Toast.LENGTH_SHORT).show();
                } else if (direction == ItemTouchHelper.RIGHT) {
                    ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Message", msg.getMessage());
                    clipboard.setPrimaryClip(clip);
                    chatAdapter.notifyItemChanged(pos);
                    Toast.makeText(getContext(), "Message copied", Toast.LENGTH_SHORT).show();
                }
            }
        };

        new ItemTouchHelper(itemTouchCallback).attachToRecyclerView(chatRecyclerView);
    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
