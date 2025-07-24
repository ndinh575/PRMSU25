package com.example.prmsu25.ui.register;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prmsu25.R;
import com.example.prmsu25.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.registerViewRegisterButton.setOnClickListener(v -> handleRegister());
        binding.registerViewLoginTextView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment));
        observeViewModel();
    }

    private boolean validateInput(String name, String email, String password, String confirmPassword, boolean agreedToTerms) {
        // Validate name
        if (TextUtils.isEmpty(name)) {
            binding.registerViewNameInputLayout.setError("Name is required");
            binding.registerViewNameInput.requestFocus();
            return false;
        } else {
            binding.registerViewNameInputLayout.setError(null);
        }

        // Validate email
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.registerViewEmailInputLayout.setError("Enter a valid email address");
            binding.registerViewEmailInput.requestFocus();
            return false;
        } else {
            binding.registerViewEmailInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 25) {
            binding.registerViewPasswordInputLayout.setError("Password must be between 6 and 25 characters");
            binding.registerViewPasswordInput.requestFocus();
            return false;
        } else {
            binding.registerViewPasswordInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            binding.registerViewPasswordConfirmInputLayout.setError("Please confirm your password");
            binding.registerViewPasswordConfirmInput.requestFocus();
            return false;
        } else if (!password.equals(confirmPassword)) {
            binding.registerViewPasswordConfirmInputLayout.setError("Passwords do not match");
            binding.registerViewPasswordConfirmInput.requestFocus();
            return false;
        } else {
            binding.registerViewPasswordConfirmInputLayout.setError(null);
        }

        if (!agreedToTerms) {
            Toast.makeText(getContext(), "You must agree to the terms and conditions", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void handleRegister() {
        String name = "";
        if (binding.registerViewNameInput.getText() != null) {
            name = binding.registerViewNameInput.getText().toString().trim();
        }

        String email = "";
        if (binding.registerViewEmailInput.getText() != null) {
            email = binding.registerViewEmailInput.getText().toString().trim();
        }
        String password = "";
        if (binding.registerViewPasswordInput.getText() != null) {
            password = binding.registerViewPasswordInput.getText().toString().trim();
        }
        String confirmPassword = "";
        if (binding.registerViewPasswordConfirmInput.getText() != null) {
            confirmPassword = binding.registerViewPasswordConfirmInput.getText().toString().trim();
        }
        boolean agreedToTerms = binding.checkBox.isChecked();

        if (validateInput(name, email, password, confirmPassword, agreedToTerms)) {
            registerViewModel.register(name, email, password);
        }
    }

    // react to registration status changes
    private void observeViewModel() {
        registerViewModel.getRegisterResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;

            switch (result.getStatus()) {
                case LOADING:
                    binding.registerViewRegisterButton.setEnabled(false);
                    break;
                case SUCCESS:
                    binding.registerViewRegisterButton.setEnabled(true);
                    Toast.makeText(getContext(), result.getData().getMessage(), Toast.LENGTH_LONG).show();

                    String email = "";
                    if (binding.registerViewEmailInput.getText() != null) {
                        email = binding.registerViewEmailInput.getText().toString().trim();
                    }
                    String password = "";
                    if (binding.registerViewPasswordInput.getText() != null) {
                        password = binding.registerViewPasswordInput.getText().toString().trim();
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("password", password);

                    Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_verifyEmailFragment, bundle);
                    break;
                case ERROR:
                    binding.registerViewRegisterButton.setEnabled(true);
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
