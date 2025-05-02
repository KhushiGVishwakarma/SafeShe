package com.example.safeshe.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.safeshe.R;
import com.example.safeshe.databinding.FragmentFeedbackBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedbackFragment extends Fragment {

    private EditText usernameEditText, emailEditText, editTextFeedback;
    private RatingBar ratingBar;
    private Button buttonSubmit;
    private TextView feedbackMessage;
    private FragmentFeedbackBinding binding;
    private FirebaseFirestore db;

    public FeedbackFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.header.toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            binding.header.collapsingToolbar.setTitle(getString(R.string.app_name));
            binding.header.collapsingToolbar.setSubtitle(getString(R.string.app_moto));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        usernameEditText = view.findViewById(R.id.usernameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        editTextFeedback = view.findViewById(R.id.editText_feedback);
        ratingBar = view.findViewById(R.id.ratingBar);
        buttonSubmit = view.findViewById(R.id.button_submit);
        feedbackMessage = view.findViewById(R.id.feedback_message);

        buttonSubmit.setOnClickListener(v -> submitFeedback());
    }

    private void submitFeedback() {
        String name = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String feedback = editTextFeedback.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (TextUtils.isEmpty(name)) {
            usernameEditText.setError("Please enter your name");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please enter your email");
            return;
        }
        if (TextUtils.isEmpty(feedback)) {
            editTextFeedback.setError("Please provide your feedback");
            return;
        }
        if (rating == 0) {
            Toast.makeText(getContext(), "Please rate your experience", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("name", name);
        feedbackData.put("email", email);
        feedbackData.put("feedback", feedback);
        feedbackData.put("rating", rating);

        db.collection("feedbacks")
                .add(feedbackData)
                .addOnSuccessListener(documentReference -> {
                    feedbackMessage.setVisibility(View.VISIBLE);
                    feedbackMessage.setText(R.string.feedback_success);
                    Toast.makeText(getContext(), "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();

                    usernameEditText.setText("");
                    emailEditText.setText("");
                    editTextFeedback.setText("");
                    ratingBar.setRating(0);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error submitting feedback: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}