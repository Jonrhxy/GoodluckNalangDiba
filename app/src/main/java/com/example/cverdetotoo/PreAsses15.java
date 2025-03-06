package com.example.cverdetotoo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.ColorStateList;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

// Firebase Firestore imports
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class PreAsses15 extends AppCompatActivity {
    // Variable to track the score
    private int score = 0;
    // Variable to store the selected answer text
    private String selectedAnswer = "";

    // Timer-related variables
    private static final long TOTAL_TIME = 20000; // 20 seconds in milliseconds
    private CountDownTimer countDownTimer;
    private TextView timerTextView;

    // The correct answer is assumed to be prebtn1d
    private int correctAnswerId = R.id.prebtn1d;
    private RadioGroup radioGroup;
    private Button submitButton, next11Button;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_pre_asses15);

        // Retrieve score from previous activity if available
        score = getIntent().getIntExtra("score", 0);

        // Initialize views
        radioGroup = findViewById(R.id.radiopreQ15);
        submitButton = findViewById(R.id.preq15);
        next11Button = findViewById(R.id.next15);
        timerTextView = findViewById(R.id.timer15);

        // Disable the Next button until an answer is submitted
        next11Button.setEnabled(false);

        // Start the 20-second timer
        startTimer();

        // Set listener for the Submit button click
        submitButton.setOnClickListener(v -> {
            if (!answered) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(PreAsses15.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Capture the selected answer text
                RadioButton selectedRadioButton = findViewById(selectedId);
                selectedAnswer = selectedRadioButton.getText().toString();
                // Cancel the timer
                countDownTimer.cancel();
                // Reset radio button colors in case user is retrying
                resetRadioButtonColors(radioGroup);
                if (selectedId == correctAnswerId) {
                    selectedRadioButton.setTextColor(Color.GREEN);
                    selectedRadioButton.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                    Toast.makeText(PreAsses15.this, "Correct!", Toast.LENGTH_SHORT).show();
                    score++;  // Increase score
                } else {
                    selectedRadioButton.setTextColor(Color.RED);
                    selectedRadioButton.setButtonTintList(ColorStateList.valueOf(Color.RED));
                    // Also highlight the correct answer in green
                    RadioButton correctRadioButton = findViewById(correctAnswerId);
                    correctRadioButton.setTextColor(Color.GREEN);
                    correctRadioButton.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                    Toast.makeText(PreAsses15.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
                // Mark as answered, disable further selections, disable submit button and enable Next button
                answered = true;
                disableRadioGroup();
                submitButton.setEnabled(false);
                next11Button.setEnabled(true);
            }
        });

        // Set listener for the Next button click to store result in Firestore and move to the next activity
        next11Button.setOnClickListener(v -> {
            if (!answered) {
                Toast.makeText(PreAsses15.this, "Please submit your answer first", Toast.LENGTH_SHORT).show();
                return;
            }
            // Fetch the correct answer's text for storing
            RadioButton correctRadioButton = findViewById(correctAnswerId);
            String correctAnswerText = correctRadioButton.getText().toString();
            // Create a Firestore instance
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            // Prepare data to store
            Map<String, Object> result = new HashMap<>();
            result.put("score", score);
            result.put("selectedAnswer", selectedAnswer);
            result.put("correctAnswer", correctAnswerText);
            result.put("timestamp", FieldValue.serverTimestamp());
            // Save the result document to the "quiz" collection
            db.collection("quiz")
                    .add(result)
                    .addOnSuccessListener(documentReference -> {
                        // On success, move to the next activity
                        Intent intent = new Intent(PreAsses15.this, PreAssess1After.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(PreAsses15.this, "Error saving result", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    /**
     * Starts the countdown timer for 20 seconds.
     */
    private void startTimer() {
        countDownTimer = new CountDownTimer(TOTAL_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                timerTextView.setText("Time left: " + secondsLeft + "s");
            }
            @Override
            public void onFinish() {
                timerTextView.setText("Time's up!");
                disableRadioGroup();
                // Automatically select and show the correct answer when time runs out
                radioGroup.check(correctAnswerId);
                answered = true;
                submitButton.setEnabled(false);
                next11Button.setEnabled(true); // Enable Next button on auto-submission
                Toast.makeText(PreAsses15.this, "Time is up! Correct answer is shown.", Toast.LENGTH_SHORT).show();
                // Highlight the correct answer in green
                RadioButton correctRadioButton = findViewById(correctAnswerId);
                correctRadioButton.setTextColor(Color.GREEN);
                correctRadioButton.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            }
        }.start();
    }

    /**
     * Disables all RadioButtons in the RadioGroup to prevent further changes.
     */
    private void disableRadioGroup() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            child.setEnabled(false);
        }
    }

    /**
     * Resets the text color and button tint of all RadioButtons in the RadioGroup.
     */
    private void resetRadioButtonColors(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            if (radioGroup.getChildAt(i) instanceof RadioButton) {
                RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
                rb.setTextColor(Color.BLACK);
                rb.setButtonTintList(null);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
