package com.example.cverdetotoo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class BadgesFragment extends Fragment {

    private ImageView ivBack;
    private TextView tvTitle;
    private CardView cardContainer;
    private GridLayout gridBadges;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // 1) Create the root ConstraintLayout programmatically
        ConstraintLayout constraintLayout = new ConstraintLayout(requireContext());
        constraintLayout.setId(View.generateViewId());
        constraintLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        // Set background (leaderboardsbg)
        constraintLayout.setBackground(
                ContextCompat.getDrawable(requireContext(), R.drawable.leaderboardsbg)
        );

        // 2) Create the Back Arrow ImageView
        ivBack = new ImageView(requireContext());
        ivBack.setId(View.generateViewId());
        ivBack.setImageResource(R.drawable.arrow_back);
        ivBack.setContentDescription("Back Button");
        ivBack.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        // Add it to the root layout
        constraintLayout.addView(ivBack);

        // 3) Create the "BADGES" TextView
        tvTitle = new TextView(requireContext());
        tvTitle.setId(View.generateViewId());
        tvTitle.setText("BADGES");
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setGravity(Gravity.CENTER);
        // Make text bold
        tvTitle.setTypeface(tvTitle.getTypeface(), android.graphics.Typeface.BOLD);
        tvTitle.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));
        // Add it to the root layout
        constraintLayout.addView(tvTitle);

        // 4) Create the CardView container
        cardContainer = new CardView(requireContext());
        cardContainer.setId(View.generateViewId());
        cardContainer.setUseCompatPadding(true);

        // 5) Create the GridLayout for badges
        gridBadges = new GridLayout(requireContext());
        gridBadges.setId(View.generateViewId());
        gridBadges.setColumnCount(2);
        // rowCount="2" in XML, but we have 8 items. It will auto-flow to extra rows.
        // If you prefer exactly 4 rows x 2 columns, you can do: gridBadges.setRowCount(4);
        gridBadges.setUseDefaultMargins(true);
        gridBadges.setAlignmentMode(GridLayout.ALIGN_MARGINS);
        // Set background (bgbadge)
        gridBadges.setBackground(ContextCompat.getDrawable(
                requireContext(), R.drawable.bgbadge
        ));
        // Equivalent to android:padding="45dp"
        int padding45dp = dpToPx(45);
        gridBadges.setPadding(padding45dp, padding45dp, padding45dp, padding45dp);

        // Add the GridLayout into the CardView
        cardContainer.addView(gridBadges);

        // Add the CardView to the root layout
        constraintLayout.addView(cardContainer);

        // 6) Dynamically add badge items to the GridLayout
        createBadgeItem(R.drawable.bggreenb, R.drawable.ecogreen, "Badge 1");
        createBadgeItem(R.drawable.bgblueb, R.drawable.ecoblue, "Badge 2");
        createBadgeItem(R.drawable.bgbrownb, R.drawable.ecobrown, "Badge 3");
        createBadgeItem(R.drawable.bggoldb, R.drawable.ecogold, "Badge 4");
        // Locked badges
        createBadgeItem(R.drawable.badgelock, 0, "Locked Badge");
        createBadgeItem(R.drawable.badgelock, 0, "Locked Badge");
        createBadgeItem(R.drawable.badgelock, 0, "Locked Badge");
        createBadgeItem(R.drawable.badgelock, 0, "Locked Badge");

        // 7) Apply ConstraintLayout rules
        // -- Back Arrow
        ConstraintLayout.LayoutParams backParams = new ConstraintLayout.LayoutParams(
                dpToPx(32),
                dpToPx(32)
        );
        backParams.topToTop = constraintLayout.getId();
        backParams.leftToLeft = constraintLayout.getId();
        backParams.setMargins(dpToPx(16), dpToPx(16), 0, 0);
        ivBack.setLayoutParams(backParams);

        // -- Title TextView
        ConstraintLayout.LayoutParams titleParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        titleParams.topToTop = constraintLayout.getId();
        titleParams.leftToLeft = constraintLayout.getId();
        titleParams.rightToRight = constraintLayout.getId();
        tvTitle.setLayoutParams(titleParams);

        // -- CardView container
        ConstraintLayout.LayoutParams cardParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
        );
        cardParams.topToBottom = tvTitle.getId();
        cardParams.bottomToBottom = constraintLayout.getId();
        cardParams.leftToLeft = constraintLayout.getId();
        cardParams.rightToRight = constraintLayout.getId();
        cardParams.setMargins(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));
        cardContainer.setLayoutParams(cardParams);

        // 8) Set up the arrow-back behavior
        ivBack.setOnClickListener(v -> {
            // If you want to pop this fragment off the stack:
            requireActivity().onBackPressed();
        });

        // Finally, return the entire programmatic layout as the Fragment’s view
        return constraintLayout;
    }

    /**
     * After the Fragment's view is created, we apply window insets (edge-to-edge).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Apply insets to this Fragment’s root view
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return windowInsets;
        });
    }

    /**
     * Helper method to create a badge item with a background and optional inner icon,
     * then add it to the GridLayout.
     */
    private void createBadgeItem(@DrawableRes int backgroundRes,
                                 @DrawableRes int iconRes,
                                 String contentDescription) {

        // 1) FrameLayout as container
        FrameLayout frameLayout = new FrameLayout(requireContext());
        GridLayout.LayoutParams flParams = new GridLayout.LayoutParams();
        flParams.width = dpToPx(94);
        flParams.height = dpToPx(94);
        // Margin = 20dp on all sides
        flParams.setMargins(dpToPx(20), dpToPx(20), dpToPx(20), dpToPx(20));
        frameLayout.setLayoutParams(flParams);

        // 2) Background ImageView
        ImageView bgImage = new ImageView(requireContext());
        bgImage.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        bgImage.setImageResource(backgroundRes);
        bgImage.setContentDescription(contentDescription + " background");
        frameLayout.addView(bgImage);

        // 3) Optional inner image
        if (iconRes != 0) {
            FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(
                    dpToPx(70),
                    dpToPx(70),
                    Gravity.CENTER
            );
            // Mimic the XML’s negative left margin & bottom margin
            iconParams.leftMargin = dpToPx(-4);  // approximate for -4sp
            iconParams.bottomMargin = dpToPx(10);

            ImageView iconImage = new ImageView(requireContext());
            iconImage.setLayoutParams(iconParams);
            iconImage.setImageResource(iconRes);
            iconImage.setContentDescription(contentDescription + " inner image");
            frameLayout.addView(iconImage);
        }

        // 4) Add the completed FrameLayout (badge) to the GridLayout
        gridBadges.addView(frameLayout);
    }

    /**
     * Utility to convert dp to pixels.
     */
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }
}
