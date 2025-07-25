package com.example.prmsu25.data.repository;

import android.util.Log;

import com.example.prmsu25.data.model.response.JobResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.api.JobApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobRepository {
    private final JobApiService apiService;

    public JobRepository(JobApiService apiService) {
        this.apiService = apiService;
    }

    public void getJobs(int page, int limit, String keywords, RepositoryCallback<JobResponse> callback) {
        callback.onResult(NetworkResult.loading());

        apiService.getJobs(page, limit, keywords).enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Error"));
                    Log.d("ERROR", "Error");
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }

    public void getRecommendedJobs(JobCallback<JobResponse> callback) {
        callback.onResult(NetworkResult.loading());
        apiService.getRecommendedJobs().enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Failed to fetch recommended jobs."));
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }

    public void getAIRecommendedJobs(JobCallback<JobResponse> callback) {
        callback.onResult(NetworkResult.loading());
        apiService.getAIRecommendedJobs().enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Failed to fetch AI-recommended jobs."));
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }

}
