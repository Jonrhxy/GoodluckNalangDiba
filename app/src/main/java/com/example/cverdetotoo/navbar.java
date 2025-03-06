package com.example.cverdetotoo;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Stack;

public class navbar extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Stack<Integer> fragmentStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar2); // Layout must include fragment_container and bottom_navigation

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load the home fragment by default.
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), R.id.nav_home);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            // Avoid reloading the same fragment.
            if (!fragmentStack.isEmpty() && fragmentStack.peek() == itemId) {
                return true;
            }

            Fragment selectedFragment = null;
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_game) {
                selectedFragment = new GameFragment();
            } else if (itemId == R.id.nav_rank) {
                selectedFragment = new LeaderBoardsFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment, itemId);
            }
            // Optionally, call highlightSelectedItem(itemId) here.
            return true;
        });

        checkForDiscountPopup();
    }

    private void loadFragment(Fragment fragment, int itemId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        if (fragmentStack.isEmpty() || fragmentStack.peek() != itemId) {
            fragmentStack.push(itemId);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentStack.size() > 1) {
            fragmentStack.pop(); // Remove current fragment.
            int previousItemId = fragmentStack.peek();

            Fragment previousFragment = null;
            if (previousItemId == R.id.nav_home) {
                previousFragment = new HomeFragment();
            } else if (previousItemId == R.id.nav_game) {
                previousFragment = new GameFragment();
            } else if (previousItemId == R.id.nav_rank) {
                previousFragment = new LeaderBoardsFragment();
            } else if (previousItemId == R.id.nav_profile) {
                previousFragment = new ProfileFragment();
            }

            if (previousFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, previousFragment)
                        .commit();
                bottomNavigationView.setSelectedItemId(previousItemId);
            }
        } else {
            super.onBackPressed();
        }
    }

    private Drawable getGreenCircleIcon(int iconResId) {
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(ContextCompat.getColor(this, R.color.red));
        circle.setSize(100, 100);

        Drawable icon = ContextCompat.getDrawable(this, iconResId);
        if (icon != null) {
            icon.mutate();
            icon.setTint(ContextCompat.getColor(this, R.color.white));
        }

        Drawable[] layers = new Drawable[]{circle, icon};
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setLayerGravity(0, Gravity.CENTER);
        layerDrawable.setLayerGravity(1, Gravity.CENTER);

        return layerDrawable;
    }

    private void checkForDiscountPopup() {
        // Implement your discount popup logic here.
    }
}
