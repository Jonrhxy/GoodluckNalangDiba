package com.example.cverdetotoo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Loadingpage extends AppCompatActivity {

    private VideoView videoView;
    private ProgressBar progressBar;
    private static final int LOADING_TIME = 3000; // 3 seconds delay
    private static final String TAG = "LoadingPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingpage);

        // Initialize VideoView and ProgressBar
        videoView = findViewById(R.id.videoViewBackground);
        progressBar = findViewById(R.id.progressBar);

        if (videoView == null || progressBar == null) {
            Log.e(TAG, "VideoView or ProgressBar is not initialized.");
            return;
        }

        // Hide ProgressBar as it will remain invisible
        progressBar.setVisibility(View.INVISIBLE);

        // Set up VideoView with background video
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mbgblur);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });

        // Delay for 3 seconds then redirect to navbar
        new Handler().postDelayed(() -> navigateTo(navbar.class), LOADING_TIME);
    }

    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(Loadingpage.this, navbar.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}
