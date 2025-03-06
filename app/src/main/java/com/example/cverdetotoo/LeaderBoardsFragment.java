package com.example.cverdetotoo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardsFragment extends Fragment {

    // Dedicated views for the top 4 positions
    private TextView firstPlaceName, firstPlaceScore;
    private TextView secondPlaceName, secondPlaceScore;
    private TextView thirdPlaceName, thirdPlaceScore;
    private TextView fourthPlaceName, fourthPlaceScore;

    // Container for ranking from 5th position onward
    private LinearLayout rvLeaderboard;

    // Firestore instance
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout (make sure the layout file name matches)
        View view = inflater.inflate(R.layout.activity_leader_boards, container, false);

        // Bind UI elements from the layout
        firstPlaceName = view.findViewById(R.id.tvFirstPlaceName);
        firstPlaceScore = view.findViewById(R.id.tvFirstPlacePoints);
        secondPlaceName = view.findViewById(R.id.tvSecondPlaceName);
        secondPlaceScore = view.findViewById(R.id.tvSecondPlacePoints);
        thirdPlaceName = view.findViewById(R.id.tvThirdPlaceName);
        thirdPlaceScore = view.findViewById(R.id.tvThirdPlacePoints);
        fourthPlaceName = view.findViewById(R.id.tvFourthPlaceName);
        fourthPlaceScore = view.findViewById(R.id.tvFourthPlacePoints);
        rvLeaderboard = view.findViewById(R.id.rvLeaderboard);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Load leaderboard data from the "Users" collection
        loadLeaderBoardData();

        return view;
    }

    // Fetch leaderboard data from the "Users" collection
    private void loadLeaderBoardData() {
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<UserData> leaderboardData = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.contains("highScore")) {
                                // Try to get the username field; if null, fallback to document ID
                                String username = document.getString("username");
                                if (username == null) {
                                    username = document.getId();
                                }
                                // Get the high score (defaulting to 0 if null)
                                int score = document.getLong("highScore") != null
                                        ? document.getLong("highScore").intValue()
                                        : 0;
                                leaderboardData.add(new UserData(username, score));
                            }
                        }
                        // Sort the list by highScore in descending order
                        leaderboardData.sort((a, b) -> Integer.compare(b.highScore, a.highScore));
                        updateLeaderboardUI(leaderboardData);
                    } else {
                        Toast.makeText(getContext(),
                                "Error loading leaderboard data: " + task.getException(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Update the UI with leaderboard data
    private void updateLeaderboardUI(List<UserData> leaderboardData) {
        // Clear any existing views from the dynamic container
        rvLeaderboard.removeAllViews();

        for (int i = 0; i < leaderboardData.size(); i++) {
            UserData user = leaderboardData.get(i);
            if (i == 0) {
                firstPlaceName.setText(user.username);
                firstPlaceScore.setText(user.highScore + " HIGH SCORE");
            } else if (i == 1) {
                secondPlaceName.setText(user.username);
                secondPlaceScore.setText(user.highScore + " HIGH SCORE");
            } else if (i == 2) {
                thirdPlaceName.setText(user.username);
                thirdPlaceScore.setText(user.highScore + " HIGH SCORE");
            } else if (i == 3) {
                fourthPlaceName.setText(user.username);
                fourthPlaceScore.setText(user.highScore + " HIGH SCORE");
            } else {
                // For rank 5 and beyond, dynamically create a row layout
                LinearLayout rowLayout = new LinearLayout(getContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setPadding(16, 8, 16, 8);
                rowLayout.setGravity(Gravity.CENTER_VERTICAL);
                // Alternate background color for better readability
                String bgColor = (i % 2 == 0) ? "#DFF8E7" : "#C8E6C9";
                rowLayout.setBackgroundColor(Color.parseColor(bgColor));

                LinearLayout.LayoutParams rowParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                rowLayout.setLayoutParams(rowParams);

                // TextView for rank and username
                TextView tvName = new TextView(getContext());
                tvName.setText((i + 1) + ". " + user.username);
                tvName.setTextSize(16);
                tvName.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams nameParams =
                        new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                tvName.setLayoutParams(nameParams);

                // TextView for the score
                TextView tvScore = new TextView(getContext());
                tvScore.setText(user.highScore + " HIGH SCORE");
                tvScore.setTextSize(14);
                tvScore.setTextColor(Color.DKGRAY);

                // Add the TextViews to the row layout
                rowLayout.addView(tvName);
                rowLayout.addView(tvScore);

                // Add the row layout to the rvLeaderboard container
                rvLeaderboard.addView(rowLayout);
            }
        }
    }

    // Data model for user leaderboard data
    public static class UserData {
        public String username;
        public int highScore;

        public UserData() {
            // Default constructor required for Firestore
        }

        public UserData(String username, int highScore) {
            this.username = username;
            this.highScore = highScore;
        }
    }
}
