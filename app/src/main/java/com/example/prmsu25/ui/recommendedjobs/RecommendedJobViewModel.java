package com.example.prmsu25.ui.recommendedjobs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.response.JobResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.JobApiService;
import com.example.prmsu25.data.repository.JobRepository;

public class RecommendedJobViewModel extends ViewModel {

    private final MutableLiveData<NetworkResult<JobResponse>> _jobsResult = new MutableLiveData<>();
    public final LiveData<NetworkResult<JobResponse>> jobsResult = _jobsResult;

    private final JobRepository jobRepository;

    public RecommendedJobViewModel() {
        JobApiService apiService = RetrofitClient.getRetrofit().create(JobApiService.class);
        this.jobRepository = new JobRepository(apiService);
    }

    public void loadJobs(boolean isAiMode) {
        if (isAiMode) {
            fetchAIRecommendedJobs();
        } else {
            fetchRecommendedJobs();
        }
    }

    private void fetchAIRecommendedJobs() {
        jobRepository.getAIRecommendedJobs(result -> _jobsResult.postValue(result));
    }

    private void fetchRecommendedJobs() {
        jobRepository.getRecommendedJobs(result -> _jobsResult.postValue(result));
    }
}
