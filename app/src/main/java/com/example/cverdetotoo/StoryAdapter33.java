package com.example.cverdetotoo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.List;

public class StoryAdapter33 extends FragmentStateAdapter {

    private List<Integer> storyImages;

    public StoryAdapter33(@NonNull FragmentActivity fragmentActivity, List<Integer> storyImages) {
        super(fragmentActivity);
        this.storyImages = storyImages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create a new instance of StoryFragment with the image resource ID for this position.
        return StoryFragment.newInstance(storyImages.get(position));
    }

    @Override
    public int getItemCount() {
        return storyImages.size();
    }
}
