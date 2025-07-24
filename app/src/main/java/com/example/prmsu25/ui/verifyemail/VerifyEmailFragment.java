package com.example.prmsu25.ui.verifyemail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.prmsu25.R;
import com.example.prmsu25.databinding.FragmentVerifyEmailBinding;

public class VerifyEmailFragment extends Fragment {
    private FragmentVerifyEmailBinding binding;
    private VerifyEmailViewModel verfifyEmailViewModel;
    private String email;
    private String password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString("email");
            password = getArguments().getString("password");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerifyEmailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        verfifyEmailViewModel = new ViewModelProvider(requireActivity()).get(VerifyEmailViewModel.class);

        binding.verifyEmailFragmentVerifyButton.setOnClickListener(v -> {
            String code = "";
            if (binding.verifyEmailFragmentVerifyCode.getText() != null) {
                code = binding.verifyEmailFragmentVerifyCode.getText().toString().trim();
            }
            if (code.isEmpty()) {
                binding.verifyEmailFragmentVerifyCode.setError("Code cannot be empty");
                return;
            }
            if (email != null && password != null) {
                verfifyEmailViewModel.verifyEmail(email, code, password);
            }
        });

        observeViewModel(view);
    }

    private void observeViewModel(View view) {
        verfifyEmailViewModel.getVerifyResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            switch (result.getStatus()) {
                case LOADING:
                    binding.verifyEmailFragmentVerifyButton.setEnabled(false);
                    break;
                case SUCCESS:
                    binding.verifyEmailFragmentVerifyButton.setEnabled(true);
                    Toast.makeText(getContext(), result.getData().getMessage(), Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.action_verifyEmailFragment_to_loginFragment);
                    break;
                case ERROR:
                    binding.verifyEmailFragmentVerifyButton.setEnabled(true);
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        verfifyEmailViewModel.resetVerifyResult();
        binding = null;
    }

}