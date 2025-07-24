package com.example.prmsu25.ui.jobdetail;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prmsu25.databinding.FragmentJobDetailBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmsu25.data.model.JobDetail;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.databinding.FragmentJobDetailBinding;
import com.example.prmsu25.databinding.ItemRowInfoBinding;
import com.example.prmsu25.ui.jobdetail.JobDetailViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class JobDetailFragment extends Fragment {

    private FragmentJobDetailBinding binding;
    private JobDetailViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJobDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);

        String jobId = getArguments() != null ? getArguments().getString("jobId") : null;
        if (jobId != null) {
            observeJobDetail();
            viewModel.fetchJobDetail(jobId);
        }
    }

    private void observeJobDetail() {
        viewModel.getJobDetailLiveData().observe(getViewLifecycleOwner(), result -> {
            switch (result.getStatus()) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.scrollContent.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.scrollContent.setVisibility(View.VISIBLE);
                    bindJobData(result.getData());
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.scrollContent.setVisibility(View.GONE);
                    binding.tvError.setVisibility(View.VISIBLE);
                    binding.tvError.setText(result.getMessage());
                    break;
            }
        });
    }

    private void bindJobData(JobDetail job) {
        binding.tvTitle.setText(job.getTitle());

        binding.tvDescription.setText(job.getDescription());
        binding.tvRequirement.setText(job.getRequirements().isEmpty() ? "Không yêu cầu" : job.getRequirements());

        setBoldText(binding.rowSalary, "Salary", job.getSalary());
        setBoldText(binding.rowExperience, "Experience", job.getExperience().isEmpty() ? "Không yêu cầu" : job.getExperience());
        setBoldText(binding.rowQuantity, "Quantity", String.valueOf(job.getQuantity()));
        setBoldText(binding.rowLevel, "Level", job.getLevel());
        setBoldText(binding.rowIndustry, "Industry", job.getIndustry());
        setBoldText(binding.rowPosition, "Position", job.getPosition());
        setBoldText(binding.rowDeliveryTime, "Delivery Time", job.getDeliveryTime());

        setBoldText(binding.rowLocation, "Location", job.getLocation());
        setBoldText(binding.rowDeadline, "Deadline", formatDate(job.getDeadline()));
        setBoldText(binding.rowCreatedAt, "Created At", formatDate(job.getCreatedAt()));
    }

    private void setBoldText(ItemRowInfoBinding rowBinding, String label, String value) {
        rowBinding.tvLabel.setText(label);
        rowBinding.tvValue.setText(value);
    }

    private String formatDate(String isoDate) {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = isoFormat.parse(isoDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            return outputFormat.format(date);
        } catch (Exception e) {
            return isoDate;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

