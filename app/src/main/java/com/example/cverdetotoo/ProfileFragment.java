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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    private ImageButton ivLeftArrow, ivRightArrow;
    private TextView tvMonthYear;
    private Calendar calendar;

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
        // --------------------- Gift Icon Popup ---------------------
        LinearLayout giftLayout = view.findViewById(R.id.giftLayout);
        giftLayout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Gift");
            builder.setMessage("You have a new gift!");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        // --------------------- Settings Icon Navigation ---------------------
        // Note: We now reference the FrameLayout with the ID 'settingsLayout'
        FrameLayout settingsLayout = view.findViewById(R.id.settingsLayout);
        settingsLayout.setOnClickListener(v -> {
            // Launch SettingsActivity (create this activity separately and add it to your manifest)
            startActivity(new Intent(getActivity(), Settings.class));
        });

        // --------------------- "See all" Clickable Text ---------------------
        TextView textSeeAll = view.findViewById(R.id.textSeeAll);
        textSeeAll.setOnClickListener(v -> {
            // Replace the container with the BadgesFragment
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.constraintLayout, new BadgesFragment())
                    .commit();
        });

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
