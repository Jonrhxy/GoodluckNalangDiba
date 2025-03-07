package com.example.cverdetotoo; // Replace with your actual package name

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

public class ActivityLogFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout (ensure the XML file name matches, e.g., fragment_activity_log.xml)
        View view = inflater.inflate(R.layout.activity_log_fragment, container, false);

        // Initialize the top app bar from the inflated view
        MaterialToolbar topAppBar = view.findViewById(R.id.topAppBar);

        // Set a click listener on the navigation icon (back arrow)
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Option 1: If using the back stack, pop the fragment
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                // Option 2: Alternatively, call getActivity().onBackPressed();
                // getActivity().onBackPressed();
            }
        });

        return view;
    }
}
