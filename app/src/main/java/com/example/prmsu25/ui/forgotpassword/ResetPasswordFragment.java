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
import com.example.prmsu25.databinding.FragmentResetPasswordBinding;

public class ResetPasswordFragment extends Fragment {

    private FragmentResetPasswordBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClickListeners();
    }

    private void setupClickListeners() {
        // Complete button click
        binding.btnComplete.setOnClickListener(v -> {
            String newPassword = binding.etNewPassword.getText().toString().trim();
            String confirmPassword = binding.etConfirmPassword.getText().toString().trim();

            if (validatePasswords(newPassword, confirmPassword)) {
                // Show success message
                Toast.makeText(getContext(), "Mật khẩu đã được đặt lại thành công!", Toast.LENGTH_SHORT).show();
                
                // Navigate back to login
                Navigation.findNavController(v).navigate(R.id.loginFragment);
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

    private boolean validatePasswords(String newPassword, String confirmPassword) {
        if (newPassword.isEmpty()) {
            binding.etNewPassword.setError("Vui lòng nhập mật khẩu mới");
            return false;
        }

        if (newPassword.length() < 6) {
            binding.etNewPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }

        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.setError("Vui lòng nhập lại mật khẩu");
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            binding.etConfirmPassword.setError("Mật khẩu không khớp");
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
