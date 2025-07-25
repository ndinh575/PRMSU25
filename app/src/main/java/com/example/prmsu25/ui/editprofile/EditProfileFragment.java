package com.example.prmsu25.ui.editprofile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.prmsu25.MainActivity;
import com.example.prmsu25.R;
import com.example.prmsu25.data.model.City;
import com.example.prmsu25.data.model.District;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.databinding.FragmentEditProfileBinding;
import com.example.prmsu25.utils.UserSessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private EditProfileViewModel viewModel;

    private UserSessionManager userSessionManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        userSessionManager = new UserSessionManager(getContext());
        viewModel.setCurrentUser(userSessionManager.getSavedUser());
        setupListeners();
        setupObservers();
        viewModel.fetchCities();
    }

    private void setupListeners() {
        binding.btnSave.setOnClickListener(v -> {
            if (validateInputs()) {
                Map<String, String> fields = new HashMap<>();
                fields.put("name", binding.etName.getText().toString().trim());
                fields.put("email", binding.etEmail.getText().toString().trim());
                fields.put("phone", binding.etPhone.getText().toString().trim());
                fields.put("city", binding.spinnerCity.getSelectedItem().toString());
                fields.put("district", binding.spinnerDistrict.getSelectedItem().toString());
                viewModel.updateUserProfile(fields);
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.editProfileFragment, true) // remove editProfileFragment from stack
                        .build();

                Navigation.findNavController(v).navigate(R.id.profileFragment, null, navOptions);
            }
        });

        binding.spinnerCity.setOnItemSelectedListener((viewModel.createCitySelectedListener()));
    }

    private void setupObservers() {

        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            binding.etName.setText(user.getName());
            binding.etEmail.setText(user.getEmail());
            binding.etPhone.setText(user.getPhone());
            if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                Picasso.get().load(user.getAvatarUrl()).into(binding.ivAvatar);
            }
        });

        viewModel.getUploadAvatarResult().observe(getViewLifecycleOwner(), result -> {
            if (result.getStatus() == NetworkResult.Status.SUCCESS) {
                String url = result.getData().getData().getAvatarUrl();
                Picasso.get().load(url).into(binding.ivAvatar);
            } else if (result.getStatus() == NetworkResult.Status.ERROR) {
                Toast.makeText(requireContext(), "Upload failed: " + result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getUpdateProfileResult().observe(getViewLifecycleOwner(), result -> {
            if (result.getStatus() == NetworkResult.Status.SUCCESS) {
                Toast.makeText(requireContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                userSessionManager.saveUserSession(result.getData().getData());
                ((MainActivity) requireActivity()).updateNavHeader();
                viewModel.setCurrentUser(result.getData().getData());
            } else if (result.getStatus() == NetworkResult.Status.ERROR) {
                Toast.makeText(requireContext(), "Update failed: " + result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getCitiesLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result.getStatus() == NetworkResult.Status.SUCCESS) {

                List<City> cities = result.getData();
                viewModel.setCachedCityList(cities);

                List<String> cityNames = cities
                        .stream()
                        .map(City::getName)
                        .collect(Collectors.toList());

                Log.d("TAG", cityNames.get(0));

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        cityNames
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerCity.setAdapter(adapter);
            }
        });

        viewModel.getDistrictsLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result.getStatus() == NetworkResult.Status.SUCCESS) {
                List<String> districtNames = result.getData().getDistricts()
                        .stream()
                        .map(District::getName)
                        .collect(Collectors.toList());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        districtNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerDistrict.setAdapter(adapter);
            }
        });
    }

    private boolean validateInputs() {
        boolean valid = true;
        if (TextUtils.isEmpty(binding.etName.getText())) {
            binding.etName.setError("Required");
            valid = false;
        }
        if (TextUtils.isEmpty(binding.etEmail.getText()) || !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches()) {
            binding.etEmail.setError("Required");
            valid = false;
        }
        if (TextUtils.isEmpty(binding.etPhone.getText()) || !Patterns.PHONE.matcher(binding.etPhone.getText()).matches()) {
            binding.etPhone.setError("Required");
            valid = false;
        }
        return valid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
