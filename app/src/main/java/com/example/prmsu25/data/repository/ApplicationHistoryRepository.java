package com.example.prmsu25.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.data.model.Application;
import com.example.prmsu25.data.model.response.ApplicationHistoryResponse;
import com.example.prmsu25.data.model.response.JobResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.api.ApplicationHistoryApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationHistoryRepository {
    private final ApplicationHistoryApiService apiService;

    public ApplicationHistoryRepository(ApplicationHistoryApiService apiService) {
        this.apiService = apiService;
    }

    public void getApplicationHistory(int page, int limit, String search, RepositoryCallback<ApplicationHistoryResponse> callback) {

        callback.onResult(NetworkResult.loading());

        apiService.getAppliedJobs(page, limit, search).enqueue(new Callback<ApplicationHistoryResponse>() {
            @Override
            public void onResponse(Call<ApplicationHistoryResponse> call, Response<ApplicationHistoryResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Error"));
                }
            }

            @Override
            public void onFailure(Call<ApplicationHistoryResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });

    }
}
