package com.example.prmsu25.ui.applicationhistory;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.R;
import com.example.prmsu25.data.model.Application;
import com.example.prmsu25.databinding.FragmentApplicationHistoryBinding;
import com.example.prmsu25.ui.adapter.ApplicationHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class ApplicationHistoryFragment extends Fragment {

    private FragmentApplicationHistoryBinding binding;
    private ApplicationHistoryViewModel viewModel;
    private ApplicationHistoryAdapter adapter;

    private int page = 1;
    private final int limit = 10;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private String currentSearch = "";

    public ApplicationHistoryFragment() {
        super(R.layout.fragment_application_history);
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentApplicationHistoryBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(ApplicationHistoryViewModel.class);

        initRecyclerView();
        observeViewModel();
        setupSwipeToRefresh();
        setupSearchMenu();

        loadApplications();
    }

    private void initRecyclerView() {
        adapter = new ApplicationHistoryAdapter(new ArrayList<>(), application -> {
            // TODO: Handle click if needed
        });

        binding.rvApplications.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvApplications.setAdapter(adapter);

        binding.rvApplications.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                if (dy > 0 && !isLoading && !isLastPage) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
                    if (layoutManager != null) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

                        if ((visibleItemCount + firstVisibleItemPos) >= totalItemCount - 2) {
                            isLoading = true;
                            page++;
                            loadApplications();
                        }
                    }
                }
            }
        });
    }

    private void observeViewModel() {
        viewModel.getApplicationHistoryLiveData().observe(getViewLifecycleOwner(), result -> {
            binding.swipeRefreshLayout.setRefreshing(false);
            isLoading = false;

            switch (result.getStatus()) {
                case LOADING:
                    if (page == 1) binding.progressBar.setVisibility(android.view.View.VISIBLE);
                    break;

                case SUCCESS:
                    binding.progressBar.setVisibility(android.view.View.GONE);
                    List<Application> applications = result.getData() != null ? result.getData().getData() : new ArrayList<>();
                    if (page == 1) {
                        adapter.setApplications(applications);
                    } else {
                        adapter.addApplications(applications);
                    }
                    isLastPage = applications.size() < limit;
                    break;

                case ERROR:
                    binding.progressBar.setVisibility(android.view.View.GONE);
                    Toast.makeText(requireContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            resetPaging();
            loadApplications();
        });
    }

    private void setupSearchMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
                inflater.inflate(R.menu.menu_search, menu);
                MenuItem searchItem = menu.findItem(R.id.action_search);
                androidx.appcompat.widget.SearchView searchView =
                        (androidx.appcompat.widget.SearchView) searchItem.getActionView();

                searchView.setQueryHint("Search applications...");
                searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        currentSearch = query.trim();
                        resetPaging();
                        loadApplications();
                        searchView.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.trim().isEmpty() && !currentSearch.isEmpty()) {
                            currentSearch = "";
                            resetPaging();
                            loadApplications();
                        }
                        return true;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem item) {
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void loadApplications() {
        viewModel.getApplicationHistory(page, limit, currentSearch);
    }

    private void resetPaging() {
        page = 1;
        isLastPage = false;
        isLoading = false;
        adapter.clearApplications();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
