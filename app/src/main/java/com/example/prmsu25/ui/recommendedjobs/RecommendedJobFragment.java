package com.example.prmsu25.ui.recommendedjobs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prmsu25.R;
import com.example.prmsu25.databinding.FragmentRecommendedJobsBinding;
import com.example.prmsu25.placeholder.PlaceholderContent;
import com.example.prmsu25.ui.adapter.JobAdapter;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

public class RecommendedJobFragment extends Fragment {
    private FragmentRecommendedJobsBinding binding;
    private RecommendedJobViewModel viewModel;
    private JobAdapter adapter;
    private boolean isInitialLoad = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRecommendedJobsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RecommendedJobViewModel.class);

        setupRecyclerView();
        setupSwitch();
        setupRefresh();
        observeViewModel();

        if (savedInstanceState == null) {
            loadJobs(binding.switchAiMode.isChecked());
        }
    }

    private void setupRecyclerView() {
        adapter = new JobAdapter(new ArrayList<>(), job -> {
            Bundle bundle = new Bundle();
            bundle.putString("jobId", job.getId());
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_recommendedJobFragment_to_jobDetailFragment, bundle);
        });
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setAdapter(adapter);
    }

    private void setupSwitch() {
        String aiText = getString(R.string.ai_recommended_jobs);

        binding.switchAiMode.setText(aiText);
        binding.switchAiMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isInitialLoad = true;
            loadJobs(isChecked);
            binding.switchAiMode.setText(aiText);
        });
    }

    private void setupRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            isInitialLoad = false;
            loadJobs(binding.switchAiMode.isChecked());
        });
    }

    private void loadJobs(boolean isAiMode) {
        viewModel.loadJobs(isAiMode);
    }

    private void observeViewModel() {
        viewModel.jobsResult.observe(getViewLifecycleOwner(), result -> {
            binding.swipeRefreshLayout.setRefreshing(false);

            switch (result.getStatus()) {
                case LOADING:
                    if (isInitialLoad) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.tvErrorMessage.setVisibility(View.GONE);
                        binding.list.setVisibility(View.GONE);
                    }
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.list.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setVisibility(View.GONE);
                    if (result.getData() != null && result.getData().isSuccess()) {
                        adapter.setJobs(result.getData().getData());
                    } else {
                        String errorMessage = result.getData() != null ? result.getData().getMessage() : getString(R.string.error_fetching_jobs);
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.list.setVisibility(View.GONE);
                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setText(result.getMessage());
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
