package com.example.prmsu25.ui.login;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prmsu25.R;
import com.example.prmsu25.data.model.User;
import com.example.prmsu25.databinding.FragmentLoginBinding;
import com.example.prmsu25.utils.UserSessionManager;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    private UserSessionManager userSessionManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        // Handle login button
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (validateInput(email, password)) {
                loginViewModel.login(email, password);
            }
        });

        // Handle register navigation
        binding.btnRegister.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_login_to_register)
        );

        // Observe login result
        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), result -> {
            if(result == null) return;
            switch (result.getStatus()) {
                case LOADING:
                    binding.btnLogin.setEnabled(false);
                    break;

                case SUCCESS:
                    binding.btnLogin.setEnabled(true);
                    Toast.makeText(requireContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    userSessionManager = new UserSessionManager(getContext());
                    userSessionManager.saveUserSession(result.getData().getUser().getId());
                    Navigation.findNavController(view).navigate(R.id.action_login_to_findJobs);
                    loginViewModel.resetLoginResult();
                    break;

                case ERROR:
                    binding.btnLogin.setEnabled(true);
                    Toast.makeText(requireContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError("Email không được để trống");
            return false;
        } else {
            binding.tilEmail.setError(null);
        }

        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 25) {
            binding.tilPassword.setError("Mật khẩu phải từ 6 đến 25 ký tự");
            return false;
        } else {
            binding.tilPassword.setError(null);
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}