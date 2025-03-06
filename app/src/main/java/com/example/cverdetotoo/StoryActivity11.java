package com.example.cverdetotoo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class StoryActivity11 extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private StoryAdapter storyAdapter;
    private CountDownTimer countDownTimer;
    private long remainingTime;
    private ProgressBar progressBar;
    private static final long STORY_DURATION = 5000; // 5 seconds per story

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story11);

        viewPager2 = findViewById(R.id.viewPagerStories);
        progressBar = findViewById(R.id.progressBar);

        // Set up the progress bar max and initial progress
        progressBar.setMax((int) STORY_DURATION);
        progressBar.setProgress(0);

        // Create a list of local image resource IDs (ensure these images exist in res/drawable)
        List<Integer> storyImages = new ArrayList<>();
        storyImages.add(R.drawable.trivia11);
        storyImages.add(R.drawable.trivia12);
        storyImages.add(R.drawable.trivia13);
        storyImages.add(R.drawable.trivia14);
        storyImages.add(R.drawable.trivia15);

        // Set up the adapter with the local images and attach it to the ViewPager2
        storyAdapter = new StoryAdapter(this, storyImages);
        viewPager2.setAdapter(storyAdapter);

        // Initialize remaining time for the current story and start the timer
        remainingTime = STORY_DURATION;
        startTimer();

        // Listen for page changes to reset the timer and progress bar for each new story
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                cancelTimer();
                remainingTime = STORY_DURATION; // Reset timer for the new story
                progressBar.setProgress(0);     // Reset progress bar
                startTimer();
            }
        });
    }

    // Override dispatchTouchEvent to catch all touch events on the activity.
    // When the user presses down, the timer is canceled (paused).
    // When the user releases, the timer is restarted (resumed).
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cancelTimer();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startTimer();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    // Starts the timer for the current story
    private void startTimer() {
        countDownTimer = new CountDownTimer(remainingTime, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                // Update the progress bar to show elapsed time
                int progress = (int) (STORY_DURATION - millisUntilFinished);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                // Ensure the progress bar is full when the timer finishes
                progressBar.setProgress((int) STORY_DURATION);
                int currentPage = viewPager2.getCurrentItem();
                if (currentPage < storyAdapter.getItemCount() - 1) {
                    viewPager2.setCurrentItem(currentPage + 1, true);
                } else {
                    finish(); // Finish activity if there are no more stories
                }
            }
        }.start();
    }

    // Cancels the current timer
    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
