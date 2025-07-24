package com.example.prmsu25.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.data.model.JobDetail;
import com.example.prmsu25.data.model.response.ApplicationResponse;
import com.example.prmsu25.data.model.response.JobDetailResponse;
import com.example.prmsu25.data.network.api.JobApiService;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.NetworkResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public void applyForJob(String jobId, MultipartBody.Part resumeFile, RequestBody applicantId, JobRepository.JobCallback callback) {
        callback.onResult(NetworkResult.loading());

        apiService.applyJob(jobId, resumeFile, applicantId).enqueue(new Callback<ApplicationResponse>() {
            @Override
            public void onResponse(Call<ApplicationResponse> call, Response<ApplicationResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Failed to apply for job"));
                }
            }

            @Override
            public void onFailure(Call<ApplicationResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }

    public interface JobCallback<T> {
        void onResult(NetworkResult<T> result);
    }
}
