package com.example.prmsu25.ui.forgotpassword;

import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prmsu25.R;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.AuthApiService;
import com.example.prmsu25.data.repository.AuthRepository;
import com.example.prmsu25.databinding.FragmentForgotPasswordBinding;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;

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

        viewModel = new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);

        binding.btnReset.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.setError("Invalid email");
                return;
            }
            viewModel.forgotPassword(email);
        });

        viewModel.getForgotResult().observe(getViewLifecycleOwner(), result -> {
            if(result == null) return;
            switch (result.getStatus()) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    String message = result.getData() != null ? result.getData().getSuccess() : "Success!";
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                    viewModel.resetForgotResult();

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show();
                    }

                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.mobile_navigation, true)
                            .build();

                    Navigation.findNavController(view).navigate(R.id.loginFragment, null, navOptions);
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

}