package com.example.prmsu25.ui.findjobs;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.R;
import com.example.prmsu25.data.model.Job;
import com.example.prmsu25.databinding.FragmentFindJobsBinding;
import com.example.prmsu25.ui.adapter.JobAdapter;

import java.util.ArrayList;
import java.util.List;

public class FindJobsFragment extends Fragment {
    private FragmentFindJobsBinding binding;
    private FindJobsViewModel viewModel;
    private JobAdapter adapter;
    private int page = 1;
    private final int limit = 10;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private String currentKeyword = "";

    public FindJobsFragment() {
        super(R.layout.fragment_find_jobs);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentFindJobsBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(FindJobsViewModel.class);

        setupRecyclerView();
        setupObservers();
        setupRefresh();
        setupMenuProvider();

        fetchJobs();
    }

    private void setupMenuProvider() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu);
                MenuItem searchItem = menu.findItem(R.id.action_search);

                androidx.appcompat.widget.SearchView searchView =
                        (androidx.appcompat.widget.SearchView) searchItem.getActionView();

                searchView.setQueryHint("Search jobs...");
                searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        currentKeyword = query.trim();
                        resetState();
                        fetchJobs();
                        searchView.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.trim().isEmpty() && !currentKeyword.isEmpty()) {
                            currentKeyword = "";
                            resetState();
                            fetchJobs();
                        }
                        return true;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void setupRecyclerView() {
        adapter = new JobAdapter(new ArrayList<>(), job -> {
            // Navigate to JobDetailFragment and pass job ID
            Bundle bundle = new Bundle();
            bundle.putString("jobId", job.getId());
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_findJobs_to_jobDetail, bundle);
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager != null) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                        if (!isLoading && !isLastPage &&
                                (visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 2) {
                            isLoading = true;
                            page++;
                            fetchJobs();
                        }
                    }
                }
            }
        });
    }

    private void setupObservers() {
        viewModel.getJobsLiveData().observe(getViewLifecycleOwner(), result -> {
            binding.swipeRefreshLayout.setRefreshing(false);
            isLoading = false;

            switch (result.status) {
                case LOADING:
                    if (page == 1) binding.progressBar.setVisibility(android.view.View.VISIBLE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(android.view.View.GONE);
                    List<Job> jobs = result.getData() != null ? result.getData().getData() : new ArrayList<>();
                    if (page == 1) adapter.setJobs(jobs);
                    else adapter.addJobs(jobs);
                    isLastPage = jobs.size() < limit;
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(android.view.View.GONE);
                    Toast.makeText(getContext(), result.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void fetchJobs() {
        viewModel.fetchJobs(page, limit, currentKeyword);
    }

    private void setupRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            resetState();
            fetchJobs();
        });
    }

    private void resetState() {
        page = 1;
        isLastPage = false;
        isLoading = false;
        adapter.clearJobs();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}