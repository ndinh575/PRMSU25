package com.example.prmsu25.ui.forgotpassword;

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
import com.example.prmsu25.databinding.FragmentForgotPasswordBinding;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClickListeners();
    }

    private void setupClickListeners() {
        // Reset button click
        binding.btnReset.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            
            if (validateEmail(email)) {
                // Show progress
                binding.progressBar.setVisibility(View.VISIBLE);
                
                // Simulate success after 2 seconds
                v.postDelayed(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Email đặt lại mật khẩu đã được gửi!", Toast.LENGTH_SHORT).show();
                    
                    // Navigate to reset password screen (uncomment to demo the next screen)
                    Navigation.findNavController(v).navigate(R.id.action_forgotPassword_to_resetPassword);
                    
                    // Or navigate back to login (comment out the line above and uncomment this)
                    // Navigation.findNavController(v).navigate(R.id.loginFragment);
                }, 2000);
            }
        });

        // Back to login click
        binding.tvBackToLogin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });

        // Register click
        binding.tvRegister.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.registerFragment);
        });
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            binding.etEmail.setError("Vui lòng nhập email");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Email không hợp lệ");
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