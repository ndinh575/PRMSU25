package com.example.prmsu25.ui.jobdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.JobDetail;
import com.example.prmsu25.data.model.response.ApplicationResponse;
import com.example.prmsu25.data.model.response.JobDetailResponse;
import com.example.prmsu25.data.network.api.JobApiService;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.repository.JobDetailRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailViewModel extends ViewModel {

    private final MutableLiveData<NetworkResult<JobDetail>> jobDetailLiveData = new MutableLiveData<>();

    public LiveData<NetworkResult<JobDetail>> getJobDetailLiveData() {
        return jobDetailLiveData;
    }

    public void fetchJobDetail(String jobId) {
        jobDetailLiveData.setValue(NetworkResult.loading());

        JobApiService apiService = RetrofitClient.getRetrofit().create(JobApiService.class);
        Call<JobDetailResponse> call = apiService.getJobDetail(jobId);

        call.enqueue(new Callback<JobDetailResponse>() {
            @Override
            public void onResponse(Call<JobDetailResponse> call, Response<JobDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    jobDetailLiveData.setValue(NetworkResult.success(response.body().getData()));
                } else {
                    jobDetailLiveData.setValue(NetworkResult.error("Cannot fetch job detail data"));
                }
            }

            @Override
            public void onFailure(Call<JobDetailResponse> call, Throwable t) {
                jobDetailLiveData.setValue(NetworkResult.error("Connection Error: " + t.getMessage()));
            }
        });
    }

    private final MutableLiveData<NetworkResult<ApplicationResponse>> applicationLiveData = new MutableLiveData<>();
    private final JobDetailRepository jobDetailRepository = new JobDetailRepository();

    public LiveData<NetworkResult<ApplicationResponse>> getApplicationLiveData() {
        return applicationLiveData;
    }

    public void applyForJob(String jobId, MultipartBody.Part resumeFile, RequestBody applicantId) {
        jobDetailRepository.applyForJob(jobId, resumeFile, applicantId, result -> {
            applicationLiveData.setValue(result);
        });
    }
}
