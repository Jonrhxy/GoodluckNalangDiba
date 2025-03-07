package com.example.cverdetotoo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    private ImageButton ivLeftArrow, ivRightArrow;
    private TextView tvMonthYear;
    private Calendar calendar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate and return the layout for this fragment.
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Initialize Firebase instances.
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // --------------------- Gift Icon Popup ---------------------
        LinearLayout giftLayout = view.findViewById(R.id.giftLayout);
        giftLayout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Gift")
                    .setMessage("You have a new gift!")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        // --------------------- Settings Icon Navigation ---------------------
        FrameLayout settingsLayout = view.findViewById(R.id.settingsLayout);
        settingsLayout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Settings.class));
        });

        // --------------------- "See all" Clickable Text ---------------------
        TextView textSeeAll = view.findViewById(R.id.textSeeAll);
        textSeeAll.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.constraintLayout, new BadgesFragment())
                    .commit();
        });

        // --------------------- Fetch First Name from Firestore ---------------------
        // Get reference to the TextView where the greeting will be shown.
        TextView textGreeting = view.findViewById(R.id.textGreeting);
        if (mAuth.getCurrentUser() != null) {
            // Use FirebaseUser displayName as the document ID.
            String username = mAuth.getCurrentUser().getDisplayName();
            if (username != null && !username.isEmpty()) {
                DocumentReference userDocRef = db.collection("users").document(username);
                userDocRef.get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String firstName = task.getResult().getString("firstName");
                                if (firstName != null && !firstName.isEmpty()) {
                                    textGreeting.setText("Hi, " + firstName + "!");
                                } else {
                                    textGreeting.setText("Hi!");
                                }
                            } else {
                                Toast.makeText(getActivity(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                                textGreeting.setText("Hi!");
                            }
                        });
            } else {
                textGreeting.setText("Hi!");
            }
        } else {
            textGreeting.setText("Hi, Guest!");
        }

        // --------------------- Arrow Navigation for Month/Year ---------------------
        ivLeftArrow = view.findViewById(R.id.ivLeftArrow);
        ivRightArrow = view.findViewById(R.id.ivRightArrow);
        tvMonthYear = view.findViewById(R.id.tvMonthYear);

        calendar = Calendar.getInstance();
        updateMonthYearDisplay();

        ivLeftArrow.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            updateMonthYearDisplay();
        });

        ivRightArrow.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            updateMonthYearDisplay();
        });

        // --------------------- Day Bubble Selection ---------------------
        LinearLayout activityLogLayout = view.findViewById(R.id.activityLogLayout);
        int childCount = activityLogLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View bubbleView = activityLogLayout.getChildAt(i);
            bubbleView.setOnClickListener(v -> {
                // Reset all bubbles to unselected (gray)
                for (int j = 0; j < activityLogLayout.getChildCount(); j++) {
                    View child = activityLogLayout.getChildAt(j);
                    child.setBackgroundResource(R.drawable.bgcircle_gray);
                }
                // Set the clicked bubble to selected
                v.setBackgroundResource(R.drawable.bgcircle_selected);
            });
        }
    }

    /**
     * Updates the Month/Year label based on the current calendar value.
     */
    private void updateMonthYearDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        tvMonthYear.setText(sdf.format(calendar.getTime()));
    }
}
