package com.example.prmsu25.ui.forgotpassword;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.prmsu25.R;
import com.example.prmsu25.databinding.FragmentResetPasswordBinding;
import com.example.prmsu25.data.network.api.UserApiService;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.model.response.ResetPasswordResponse;
import com.example.prmsu25.utils.UserSessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordFragment extends Fragment {

    private FragmentResetPasswordBinding binding;
    private UserApiService userApiService;
    private UserSessionManager userSessionManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        userApiService = RetrofitClient.getRetrofit().create(UserApiService.class);
        userSessionManager = new UserSessionManager(getContext());
        
        setupClickListeners();
    }

    private void setupClickListeners() {
        // Complete button
        binding.btnComplete.setOnClickListener(v -> {
            String newPassword = binding.etNewPassword.getText().toString().trim();
            String confirmPassword = binding.etConfirmPassword.getText().toString().trim();

            if (validatePasswords(newPassword, confirmPassword)) {
                resetPassword(newPassword);
            }
        });

        // Back to login
        binding.tvBackToLogin.setOnClickListener(v -> {
            // Pop back to login fragment
            Navigation.findNavController(v).popBackStack(R.id.loginFragment, false);
        });

        // Register
        binding.tvRegister.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.registerFragment);
        });
    }

    private boolean validatePasswords(String newPassword, String confirmPassword) {
        if (TextUtils.isEmpty(newPassword)) {
            binding.etNewPassword.setError("Vui lòng nhập mật khẩu mới");
            return false;
        }

        if (newPassword.length() < 6) {
            binding.etNewPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            binding.etConfirmPassword.setError("Vui lòng nhập lại mật khẩu");
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            binding.etConfirmPassword.setError("Mật khẩu không khớp");
            return false;
        }

        return true;
    }

    private void resetPassword(String newPassword) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnComplete.setEnabled(false);

        // Get token from session or arguments (for demo, using empty token)
        String token = userSessionManager.getToken(); // You might need to pass token differently
        
        Map<String, String> passwordData = new HashMap<>();
        passwordData.put("password", newPassword);

        userApiService.resetPassword(token, passwordData).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                binding.progressBar.setVisibility(View.GONE);
                binding.btnComplete.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    
                    // Navigate back to login
                    Navigation.findNavController(requireView()).popBackStack(R.id.loginFragment, false);
                } else {
                    Toast.makeText(getContext(), "Đặt lại mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.btnComplete.setEnabled(true);
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
