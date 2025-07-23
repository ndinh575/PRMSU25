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
  private RegisterViewModel mViewModel;

  private RegisterViewModel registerViewModel;

  public static RegisterFragment newInstance() {
    return new RegisterFragment();
  }

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

    binding.registerViewRegisterButton.setOnClickListener(v -> {
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
        // TODO: Implement view model
        Toast.makeText(getContext(), "Registration fields are valid!", Toast.LENGTH_SHORT).show();
      }
    });

    binding.registerViewHasAccountTextView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment));

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

        // Validate password
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 25) {
            binding.registerViewPasswordInputLayout.setError("Password must be between 6 and 25 characters");
            binding.registerViewPasswordInput.requestFocus();
            return false;
        } else {
            binding.registerViewPasswordInputLayout.setError(null);
        }

        // Validate confirm password
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

        // Validate terms and conditions
        if (!agreedToTerms) {
            Toast.makeText(getContext(), "You must agree to the terms and conditions", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
