package com.example.cverdetotoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class popupWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Enable edge-to-edge layout if desired
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_popup_welcome);

        // Find the close button in your layout and set its listener
        Button btnClose = findViewById(R.id.btnWNext);
        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create an Intent to navigate to popupWelcome activity
                Intent intent = new Intent(popupWelcome.this, popupWelcome2.class);

                // Start the new activity (popupWelcome)
                startActivity(intent);

                // Optionally, you can finish this activity if you don't want to keep it in the back stack
                finish();
            }
        });

    }
}
