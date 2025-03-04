package com.example.cverdetotoo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardsFragment extends Fragment {

    private TextView tvSecondName, tvSecondPoints, tvSecondRankTitle;
    private ImageView ivSecondAvatar;
    private TextView tvFirstName, tvFirstPoints, tvFirstRankTitle;
    private ImageView ivFirstAvatar;
    private TextView tvThirdName, tvThirdPoints, tvThirdRankTitle;
    private ImageView ivThirdAvatar;
    private RecyclerView rvLeaderboard;

    // Inflate the fragment's layout
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Replace R.layout.activity_leader_boards with your fragment layout if needed
        View view = inflater.inflate(R.layout.activity_leader_boards, container, false);

        // Initialize the top 3 views
        tvSecondName = view.findViewById(R.id.tvSecondPlaceName);
        tvSecondPoints = view.findViewById(R.id.tvSecondPlacePoints);
        tvSecondRankTitle = view.findViewById(R.id.tvSecondPlaceRank);
        ivSecondAvatar = view.findViewById(R.id.ivSecondPlace);

        tvFirstName = view.findViewById(R.id.tvFirstPlaceName);
        tvFirstPoints = view.findViewById(R.id.tvFirstPlacePoints);
        tvFirstRankTitle = view.findViewById(R.id.tvFirstPlaceRank);
        ivFirstAvatar = view.findViewById(R.id.ivFirstPlace);

        tvThirdName = view.findViewById(R.id.tvThirdPlaceName);
        tvThirdPoints = view.findViewById(R.id.tvThirdPlacePoints);
        tvThirdRankTitle = view.findViewById(R.id.tvThirdPlaceRank);
        ivThirdAvatar = view.findViewById(R.id.ivThirdPlace);


        // Sample data
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(new Player("Rae",   1290, 1, "Eco Master", R.drawable.muser));
        allPlayers.add(new Player("Gelik", 1010, 2, "Eco Hero",   R.drawable.muser));
        allPlayers.add(new Player("Nea",   1000, 3, "Eco Hero",   R.drawable.muser));
        allPlayers.add(new Player("Celine",853,  4, "Eco Leader", R.drawable.muser));
        allPlayers.add(new Player("PlayerX",750, 5, "Eco Leader", R.drawable.muser));
        allPlayers.add(new Player("PlayerY",690, 6, "Eco Leader", R.drawable.muser));
        // ... add more players as needed

        // Separate top 3 from the rest
        if (allPlayers.size() >= 3) {
            List<Player> top3 = allPlayers.subList(0, 3);
            List<Player> others = allPlayers.subList(3, allPlayers.size());

            // Populate the top 3 in the UI
            // Rank #2
            Player second = top3.get(1);
            tvSecondName.setText(second.getName());
            tvSecondPoints.setText(second.getPoints() + " pts");
            tvSecondRankTitle.setText(second.getRankTitle());
            ivSecondAvatar.setImageResource(second.getAvatarResId());

            // Rank #1
            Player first = top3.get(0);
            tvFirstName.setText(first.getName());
            tvFirstPoints.setText(first.getPoints() + " pts");
            tvFirstRankTitle.setText(first.getRankTitle());
            ivFirstAvatar.setImageResource(first.getAvatarResId());

            // Rank #3
            Player third = top3.get(2);
            tvThirdName.setText(third.getName());
            tvThirdPoints.setText(third.getPoints() + " pts");
            tvThirdRankTitle.setText(third.getRankTitle());
            ivThirdAvatar.setImageResource(third.getAvatarResId());

            // Set up the RecyclerView for ranks #4 and beyond
            rvLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
            LeaderboardAdapter adapter = new LeaderboardAdapter(others);
            rvLeaderboard.setAdapter(adapter);
        }

        return view;
    }
}
