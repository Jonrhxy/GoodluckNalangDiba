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

    // Dedicated views for top 4 leaderboard positions
    private TextView firstPlaceName, firstPlaceScore;
    private TextView secondPlaceName, secondPlaceScore;
    private TextView thirdPlaceName, thirdPlaceScore;
    private TextView fourthPlaceName, fourthPlaceScore;

    // Container for the remaining leaderboard rows (rank 5 and beyond)
    private LinearLayout rvLeaderboard;

    // Firestore instance
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate your fragment layout (ensure the layout file matches your resource name)
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

        // Load leaderboard data from Firestore
        loadLeaderBoardData();

        return view;
    }

    // Fetch leaderboard data from the "Games" collection
    private void loadLeaderBoardData() {
        db.collection("Games")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<GameData> leaderboardData = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.contains("highScore")) {
                                // Create GameData using the document ID as the username and highScore field
                                GameData gameData = new GameData(
                                        document.getId(),
                                        document.getLong("highScore").intValue()
                                );
                                leaderboardData.add(gameData);
                            }
                        }
                        // Sort the list by highScore in descending order
                        leaderboardData.sort((a, b) -> Integer.compare(b.highScore, a.highScore));
                        updateLeaderboardUI(leaderboardData);
                    } else {
                        Toast.makeText(getContext(), "Error getting leaderboard data: "
                                + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Update the UI with leaderboard data
    private void updateLeaderboardUI(List<GameData> leaderboardData) {
        // Clear any existing views in the container for the remaining entries
        rvLeaderboard.removeAllViews();

        for (int i = 0; i < leaderboardData.size(); i++) {
            GameData player = leaderboardData.get(i);

            // Assign the top four players to dedicated slots
            if (i == 0) {
                firstPlaceName.setText(player.username);
                firstPlaceScore.setText(player.highScore + " HIGH SCORE");
            } else if (i == 1) {
                secondPlaceName.setText(player.username);
                secondPlaceScore.setText(player.highScore + " HIGH SCORE");
            } else if (i == 2) {
                thirdPlaceName.setText(player.username);
                thirdPlaceScore.setText(player.highScore + " HIGH SCORE");
            } else if (i == 3) {
                fourthPlaceName.setText(player.username);
                fourthPlaceScore.setText(player.highScore + " HIGH SCORE");
            } else {
                // For players starting at rank 5 (index 4 and beyond), add them to the dynamic list
                LinearLayout playerLayout = new LinearLayout(getContext());
                playerLayout.setOrientation(LinearLayout.HORIZONTAL);
                playerLayout.setPadding(16, 8, 16, 8);
                playerLayout.setGravity(Gravity.CENTER_VERTICAL);
                // Alternate background colors for readability
                playerLayout.setBackgroundColor(Color.parseColor(i % 2 == 0 ? "#DFF8E7" : "#C8E6C9"));
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                playerLayout.setLayoutParams(layoutParams);

                // Create a TextView for the rank and username
                TextView playerNameTextView = new TextView(getContext());
                // Use (i+1) so that rank is correct (rank 5 for index 4, etc.)
                playerNameTextView.setText((i + 1) + ". " + player.username);
                playerNameTextView.setTextSize(16);
                playerNameTextView.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams nameParams =
                        new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                playerNameTextView.setLayoutParams(nameParams);

                // Create a TextView for the score
                TextView playerScoreTextView = new TextView(getContext());
                playerScoreTextView.setText(player.highScore + " HIGH SCORE");
                playerScoreTextView.setTextSize(14);
                playerScoreTextView.setTextColor(Color.DKGRAY);

                // Add the TextViews to the player's layout
                playerLayout.addView(playerNameTextView);
                playerLayout.addView(playerScoreTextView);

                // Add the player's row to the container
                rvLeaderboard.addView(playerLayout);
            }
        }
    }

    // Data model for leaderboard entries
    public static class GameData {
        public String username;
        public int highScore;

        public GameData() {
            // Default constructor required for Firestore
        }

        public GameData(String username, int highScore) {
            this.username = username;
            this.highScore = highScore;
        }
    }
}
