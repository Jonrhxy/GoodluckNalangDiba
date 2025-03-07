package com.example.cverdetotoo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Settings extends AppCompatActivity {

    // Declare views
    private ImageView ivBack;
    private CardView cardEditProfile, cardTermsConditions, cardAboutUs, cardDeleteAccount;
    private ImageButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // Ensure this matches your XML filename

        // Initialize the views
        ivBack = findViewById(R.id.ivBack);
        cardEditProfile = findViewById(R.id.cardEditProfile);
        cardTermsConditions = findViewById(R.id.cardTermsConditions);
        cardAboutUs = findViewById(R.id.cardAboutUs);
        cardDeleteAccount = findViewById(R.id.cardDeleteAccount);
        btnLogout = findViewById(R.id.btnLogout);

        // Set click listeners for each element

        // Back arrow: simply finish the activity
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Edit Profile: navigate to EditProfileActivity
        cardEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, EditProfile.class);
                startActivity(intent);
            }
        });

        // Terms & Conditions: navigate to TermsConditionsActivity
        cardTermsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, TermsAndCon.class);
                startActivity(intent);
            }
        });

        // About Us: navigate to AboutUsActivity
        cardAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, AboutUs.class);
                startActivity(intent);
            }
        });

        // Delete Account: show a confirmation dialog before deleting the account
        cardDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteAccountDialog();
            }
        });

        // Logout button: perform logout action
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
    }

    /**
     * Show a confirmation dialog before deleting the user account.
     */
    private void showDeleteAccountDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /**
     * Delete the user account.
     * (Implement your account deletion logic here)
     */
    private void deleteAccount() {
        // Sample feedback - replace with your own account deletion logic
        Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show();
        // Optionally, log the user out after deleting the account
        logoutUser();
    }

    /**
     * Log out the user and navigate to the login screen.
     */
    private void logoutUser() {
        // Implement your logout logic (e.g., clearing session data) here
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.this, Settings.class);
        startActivity(intent);
        finish();
    }
}
