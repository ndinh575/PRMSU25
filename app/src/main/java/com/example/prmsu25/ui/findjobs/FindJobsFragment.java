package com.example.prmsu25.ui.findjobs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.R;
import com.example.prmsu25.adapter.JobAdapter;
import com.example.prmsu25.api.JobApiService;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.model.Job;
import com.example.prmsu25.model.response.JobResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindJobsFragment extends Fragment {

    private RecyclerView rvJobs;
    private EditText etSearch;
    private JobApiService apiService;
    private JobAdapter adapter;

    // Phân trang
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 1;
    private final int limit = 10;

    public FindJobsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_jobs, container, false);

        rvJobs = view.findViewById(R.id.rvJobs);
        etSearch = view.findViewById(R.id.etSearch);

        rvJobs.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrofit
        apiService = RetrofitClient.getRetrofit().create(JobApiService.class);

        // Adapter rỗng + listener
        adapter = new JobAdapter(new ArrayList<>(), job -> navigateToDetail(job.id));
        rvJobs.setAdapter(adapter);

        // Gọi API ban đầu
        loadJobs("", true);

        // Scroll listener phân trang
        rvJobs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (lm != null && !isLoading && !isLastPage) {
                    int visible = lm.getChildCount();
                    int total = lm.getItemCount();
                    int firstVisible = lm.findFirstVisibleItemPosition();
                    if ((visible + firstVisible) >= total && firstVisible >= 0) {
                        currentPage++;
                        loadJobs(etSearch.getText().toString(), false);
                    }
                }
            }
        });

        // Search
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                currentPage = 1;
                isLastPage = false;
                adapter.setJobs(new ArrayList<>());
                loadJobs(s.toString(), true);
            }
        });

        return view;
    }

    private void loadJobs(String locationFilter, boolean isRefresh) {
        isLoading = true;
        Call<JobResponse> call = apiService.getJobs(currentPage, limit, locationFilter);
        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                isLoading = false;
                if (response.isSuccessful() && response.body() != null) {
                    List<Job> jobs = response.body().data;
                    if (isRefresh) {
                        adapter.setJobs(jobs);
                    } else {
                        adapter.addJobs(jobs);
                    }
                    if (jobs.size() < limit) {
                        isLastPage = true;
                    }
                } else {
                    Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                isLoading = false;
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToDetail(String jobId) {
        if (jobId == null) return;
        Bundle args = new Bundle();
        args.putString("jobId", jobId);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_findJobs_to_jobDetail, args);
    }
}
