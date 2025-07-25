package com.example.prmsu25.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prmsu25.utils.UserSessionManager;
import com.squareup.picasso.Picasso;

import com.example.prmsu25.R;
import com.example.prmsu25.data.model.User;
import com.example.prmsu25.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;

    private FragmentProfileBinding binding;

    private UserSessionManager userSessionManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userSessionManager = new UserSessionManager(getContext());
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        observeProfile();

        viewModel.fetchUserProfile();
    }

    private void observeProfile() {
        viewModel.getProfileLiveData().observe(getViewLifecycleOwner(), result -> {
            switch (result.getStatus()) {
                case LOADING:
                    // Optional: show a loading spinner
                    break;

                case SUCCESS:
                    if (result.getData() != null) {
                        User userProfile = result.getData().getData();
                        populateUI(userProfile);
                        userSessionManager.saveUserSession(userProfile);
                    }
                    break;

                case ERROR:
                    Toast.makeText(requireContext(), "Lỗi: " + result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void populateUI(User user) {
        binding.tvProfileName.setText(user.getName());
        binding.tvEmail.setText(user.getEmail());
        binding.tvPhone.setText(user.getPhone() == null ? "Chưa có số điện thoại" : user.getPhone());
        binding.tvCity.setText(user.getCity() == null ? "Chưa có địa điểm" : user.getCity());
        binding.tvDistrict.setText(user.getDistrict() == null ? "Chưa có địa điểm" : user.getDistrict());

        Picasso.get()
                .load(user.getAvatarUrl())
                .placeholder(R.drawable.ic_profile_placeholder)
                .error(R.drawable.ic_profile_placeholder)
                .resize(120, 120)
                .centerCrop()
                .into(binding.ivProfileAvatar);

        binding.btnEditProfile.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_profile_to_editProfile));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}