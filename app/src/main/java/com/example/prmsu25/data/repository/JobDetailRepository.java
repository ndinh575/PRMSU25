package com.example.prmsu25.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.data.model.JobDetail;
import com.example.prmsu25.data.model.response.JobDetailResponse;
import com.example.prmsu25.data.network.api.JobApiService;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.NetworkResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailRepository {

    private final JobApiService apiService;

    public JobDetailRepository() {
        this.apiService = RetrofitClient.getRetrofit().create(JobApiService.class);
    }

    public LiveData<NetworkResult<JobDetail>> getJobDetail(String jobId) {
        MutableLiveData<NetworkResult<JobDetail>> jobDetailLiveData = new MutableLiveData<>();
        jobDetailLiveData.setValue(NetworkResult.loading());

        apiService.getJobDetail(jobId).enqueue(new Callback<JobDetailResponse>() {
            @Override
            public void onResponse(Call<JobDetailResponse> call, Response<JobDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JobDetailResponse body = response.body();
                    if (body.isSuccess()) {
                        jobDetailLiveData.setValue(NetworkResult.success(body.getData()));
                    } else {
                        jobDetailLiveData.setValue(NetworkResult.error(body.getMessage()));
                    }
                } else {
                    jobDetailLiveData.setValue(NetworkResult.error("Job not found or unknown error"));
                }
            }

            @Override
            public void onFailure(Call<JobDetailResponse> call, Throwable t) {
                jobDetailLiveData.setValue(NetworkResult.error(t.getMessage()));
            }
        });

        return jobDetailLiveData;
    }
}
