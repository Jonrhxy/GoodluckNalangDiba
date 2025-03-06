package com.example.cverdetotoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PreAssess1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the action bar (header)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_pre_assess1);

        // Find the button by its id
        Button startButton = findViewById(R.id.prebtnstart1);

        // Set an OnClickListener on the button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start PreAsses11.class
                Intent intent = new Intent(PreAssess1.this, PreAsses11.class);
                startActivity(intent);
            }
        });
    }
}
