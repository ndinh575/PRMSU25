package com.example.prmsu25.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prmsu25.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        setupClickListeners(view);
        
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    private void setupClickListeners(View view) {
        // Edit Profile Button
        android.widget.Button btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(v).navigate(R.id.action_profile_to_editProfile);
        });

        // Add Project Button
        android.widget.Button btnAddProject = view.findViewById(R.id.btnAddProject);
        btnAddProject.setOnClickListener(v -> {
            android.widget.Toast.makeText(getContext(), "Thêm dự án mới", android.widget.Toast.LENGTH_SHORT).show();
            // TODO: Navigate to add project screen
        });
    }

}