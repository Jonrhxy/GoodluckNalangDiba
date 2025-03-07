package com.example.cverdetotoo;  // <-- Replace with your actual package name

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TermsAndCon extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvAcceptance;
    private TextView tvUse;
    private TextView tvDataCollection;
    private TextView tvUserResponsibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout resource file
        setContentView(R.layout.activity_terms_and_con);

        // Initialize views from the layout
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvAcceptance = findViewById(R.id.tvAcceptance);
        tvUse = findViewById(R.id.tvUse);
        tvDataCollection = findViewById(R.id.tvDataCollection);
        tvUserResponsibility = findViewById(R.id.tvUserResponsibility);

        // Set a click listener on the back arrow to navigate back
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // You can simply finish this activity or use onBackPressed()
                onBackPressed();
            }
        });

        // Optionally, you can change or set text programmatically:
        // tvTitle.setText("My Custom Title");
    }
}
