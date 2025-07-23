package com.example.prmsu25.ui.findjobs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.response.JobResponse;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.JobApiService;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.repository.JobRepository;

public class FindJobsViewModel extends ViewModel {
    private final MutableLiveData<NetworkResult<JobResponse>> jobsLiveData = new MutableLiveData<>();
    private final JobRepository jobRepository;

    public FindJobsViewModel() {
        JobApiService api = RetrofitClient.getRetrofit().create(JobApiService.class);
        this.jobRepository = new JobRepository(api);
    }

    public LiveData<NetworkResult<JobResponse>> getJobsLiveData() {
        return jobsLiveData;
    }

    public void fetchJobs(int page, int limit, String keywords) {
        jobRepository.getJobs(page, limit, keywords, result -> jobsLiveData.postValue(result));
    }
}
