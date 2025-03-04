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
        setContentView(R.layout.activity_main); // Make sure this layout contains fragment_container and bottom_navigation

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load the home fragment by default.
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), R.id.nav_home);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
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
            return true;
        });

        // Check if the user qualifies for the discount popup.
        checkForDiscountPopup();
    }

    // Removed the extra loadFragment overload that was causing issues.
    private void loadFragment(Fragment fragment, int itemId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        // Add the selected fragment to the stack (avoid duplicates).
        if (!fragmentStack.isEmpty() && fragmentStack.peek() == itemId) {
            return;
        }
        fragmentStack.push(itemId);
    }

    @Override
    public void onBackPressed() {
        if (fragmentStack.size() > 1) {
            fragmentStack.pop(); // Remove the current fragment.
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

    /**
     * Creates a LayerDrawable consisting of a green circle background and the original icon (tinted white) on top.
     */
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

    /**
     * Highlights the selected BottomNavigationView menu item with a green circle icon
     * and resets the others to their default icons.
     */
    private void highlightSelectedItem(int selectedItemId) {
        // Reset all icons to their default state.
        bottomNavigationView.getMenu().findItem(R.id.nav_home).setIcon(R.drawable.home);
        bottomNavigationView.getMenu().findItem(R.id.nav_game).setIcon(R.drawable.game);
        bottomNavigationView.getMenu().findItem(R.id.nav_rank).setIcon(R.drawable.podium);
        bottomNavigationView.getMenu().findItem(R.id.nav_profile).setIcon(R.drawable.nuser);

        // Set the selected icon to the green circle version.
        if (selectedItemId == R.id.nav_home) {
            bottomNavigationView.getMenu().findItem(R.id.nav_home)
                    .setIcon(getGreenCircleIcon(R.drawable.home));
        } else if (selectedItemId == R.id.nav_game) {
            bottomNavigationView.getMenu().findItem(R.id.nav_game)
                    .setIcon(getGreenCircleIcon(R.drawable.game));
        } else if (selectedItemId == R.id.nav_rank) {
            bottomNavigationView.getMenu().findItem(R.id.nav_rank)
                    .setIcon(getGreenCircleIcon(R.drawable.podium));
        } else if (selectedItemId == R.id.nav_profile) {
            bottomNavigationView.getMenu().findItem(R.id.nav_profile)
                    .setIcon(getGreenCircleIcon(R.drawable.nuser));
        }
    }

    private void checkForDiscountPopup() {
        // Implement discount popup check logic here.
    }
}
