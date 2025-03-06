package com.example.cverdetotoo;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

public class video1 extends AppCompatActivity {

    private VideoView storyVideo;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video1);

        // Initialize the VideoView and close button
        storyVideo = findViewById(R.id.story_video);
        closeButton = findViewById(R.id.close_button);

        // Load the video from the raw folder. Replace 'my_video' with your actual file name.
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid1intro);
        storyVideo.setVideoURI(videoUri);
        storyVideo.start();  // Start playing the video

        // Close the story view when the close button is pressed
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // TODO: Implement story progress (timer, animated progress bar)
        // TODO: Add gesture detection for swipe to next/previous story if required
    }
}
