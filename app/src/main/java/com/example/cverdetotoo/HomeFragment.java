package com.example.cverdetotoo;

import android.app.Activity;
import android.content.Intent;
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
        // Initialize your ViewModel if needed
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Inflate the layout using the standard inflater
        View root = inflater.inflate(R.layout.activity_home, container, false);

        // Register the ActivityResultLauncher for PreAssess1
        preAssessLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Check if PreAssess1 returned with RESULT_OK
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Launch video1 activity
                            Intent videoIntent = new Intent(getActivity(), video1.class);
                            startActivity(videoIntent);
                        }
                    }
                }
        );

        // Find the CardView with the id "vid1" in your fragment layout
        CardView vid1Card = root.findViewById(R.id.vid1);
        CardView vid2Card = root.findViewById(R.id.vid2);

        // Set an OnClickListener on the CardView to start the PreAssess1 activity
        vid1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PreAssess1.class);
                preAssessLauncher.launch(intent);
            }
        });

        // Set an OnClickListener on the CardView to start the PreAssess1 activity
        vid2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PreAssess2.class);
                preAssessLauncher.launch(intent);
            }
        });

        // Find the CardView with the id "trivia1" in your fragment layout
        CardView trivia1Card = root.findViewById(R.id.trivia1);
        CardView trivia2Card = root.findViewById(R.id.trivia2);
        CardView trivia3Card = root.findViewById(R.id.trivia3);
        CardView trivia4Card = root.findViewById(R.id.trivia4);

        // Set an OnClickListener on the CardView to start the trivia11 activity
        trivia1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent triviaIntent = new Intent(getActivity(), StoryActivity11.class);
                startActivity(triviaIntent);
            }
        });

        // Set an OnClickListener on the CardView to start the trivia11 activity
        trivia2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent triviaIntent = new Intent(getActivity(), StoryActivity22.class);
                startActivity(triviaIntent);
            }
        });

        // Set an OnClickListener on the CardView to start the trivia11 activity
        trivia3Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent triviaIntent = new Intent(getActivity(), StoryActivity33.class);
                startActivity(triviaIntent);
            }
        });

        // Set an OnClickListener on the CardView to start the trivia11 activity
        trivia4Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent triviaIntent = new Intent(getActivity(), StoryActivity44.class);
                startActivity(triviaIntent);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Hide the entire ActionBar to remove the black layout above
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
    }
}
