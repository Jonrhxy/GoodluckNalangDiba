package com.example.cverdetotoo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class HomeFragment extends Fragment {

    private ActivityResultLauncher<Intent> preAssessLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.activity_home, container, false);

        // Show the welcome popup only once (using SharedPreferences)
        showWelcomePopup();

        // Register the ActivityResultLauncher for PreAssess activities
        preAssessLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Launch video1 activity
                            Intent videoIntent = new Intent(getActivity(), video1.class);
                            startActivity(videoIntent);
                        }
                    }
                }
        );

        // Set click listeners for video cards
        CardView vid1Card = root.findViewById(R.id.vid1);
        CardView vid2Card = root.findViewById(R.id.vid2);
        vid1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PreAssess1.class);
                preAssessLauncher.launch(intent);
            }
        });
        vid2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PreAssess2.class);
                preAssessLauncher.launch(intent);
            }
        });

        // Set click listeners for trivia cards
        CardView trivia1Card = root.findViewById(R.id.trivia1);
        CardView trivia2Card = root.findViewById(R.id.trivia2);
        CardView trivia3Card = root.findViewById(R.id.trivia3);
        CardView trivia4Card = root.findViewById(R.id.trivia4);

        trivia1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent triviaIntent = new Intent(getActivity(), StoryActivity11.class);
                startActivity(triviaIntent);
            }
        });

        trivia2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent triviaIntent = new Intent(getActivity(), StoryActivity22.class);
                startActivity(triviaIntent);
            }
        });

        trivia3Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent triviaIntent = new Intent(getActivity(), StoryActivity33.class);
                startActivity(triviaIntent);
            }
        });

        trivia4Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent triviaIntent = new Intent(getActivity(), StoryActivity44.class);
                startActivity(triviaIntent);
            }
        });

        return root;
    }

    /**
     * Launches the welcome popup activity only once.
     */
    private void showWelcomePopup() {
        if (getContext() != null) {
            SharedPreferences prefs = getContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
            boolean popupShown = prefs.getBoolean("popupShown", false);
            if (!popupShown) {
                Intent intent = new Intent(getActivity(), popupWelcome.class);
                startActivity(intent);
                prefs.edit().putBoolean("popupShown", true).apply();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
    }
}
