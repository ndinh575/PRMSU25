package com.example.prmsu25.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.prmsu25.R;

public class EditProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        
        setupClickListeners(view);
        
        return view;
    }

    private void setupClickListeners(View view) {
        // Back button
        view.findViewById(R.id.btnBack).setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        // Change avatar button
        view.findViewById(R.id.btnChangeAvatar).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Chọn ảnh đại diện", Toast.LENGTH_SHORT).show();
            // TODO: Open image picker
        });

        // Save button
        view.findViewById(R.id.btnSave).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đã lưu thông tin", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).popBackStack();
        });
    }
}
