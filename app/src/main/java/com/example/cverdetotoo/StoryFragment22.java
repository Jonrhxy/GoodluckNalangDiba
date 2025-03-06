package com.example.cverdetotoo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class StoryFragment22 extends Fragment {

    private static final String ARG_IMAGE_RES = "image_res";
    private int imageRes;

    // Factory method to create a new instance of this fragment using the provided image resource ID.
    public static StoryFragment newInstance(int imageRes) {
        StoryFragment fragment = new StoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES, imageRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageRes = getArguments().getInt(ARG_IMAGE_RES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_story_fragment22, container, false);
        ImageView imageView = view.findViewById(R.id.storyImage22);
        imageView.setImageResource(imageRes);
        return view;
    }
}
