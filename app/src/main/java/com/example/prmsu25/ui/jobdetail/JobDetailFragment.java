package com.example.prmsu25.ui.jobdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmsu25.databinding.FragmentJobDetailBinding;

public class JobDetailFragment extends Fragment {

    private FragmentJobDetailBinding binding;
    private JobDetailViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJobDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        mViewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);
        
        setupViews();
        setupClickListeners();
        loadJobDetails();
    }

    private void setupViews() {
        // Hide progress bar since we're showing demo data
        binding.progressBar.setVisibility(View.GONE);
    }

    private void setupClickListeners() {
        // Back button
        binding.btnBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        // Apply Now button
        binding.btnApplyNow.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Ứng tuyển ngay - Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
        });

        // Apply Fast button
        binding.btnApplyFast.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Ứng tuyển nhanh - Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
        });

        // Save Job button
        binding.btnSave.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đã lưu công việc", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadJobDetails() {
        // Demo data - in a real app, this would come from arguments or API
        binding.tvJobTitle.setText("Tuyển nhân viên pha chế 2");
        binding.tvJobId.setText("12312 31 231 234124 1241");
        binding.tvPostDate.setText("7/8/2025");
        binding.tvSalary.setText("10,000,000 VNĐ");
        binding.tvWorkSchedule.setText("Thỏa thuận");
        binding.tvLevel.setText("Thông thường");
        binding.tvEmployerName.setText("Hiếu Ứng Viên");
        binding.tvRequirements.setText("Phục vụ / Bán hàng");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
