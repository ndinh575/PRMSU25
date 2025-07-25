package com.example.prmsu25.ui.applicationhistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.Application;
import com.example.prmsu25.data.model.response.ApplicationHistoryResponse;
import com.example.prmsu25.data.model.response.JobResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.ApplicationHistoryApiService;
import com.example.prmsu25.data.repository.ApplicationHistoryRepository;

import java.util.List;

public class ApplicationHistoryViewModel extends ViewModel {
    private final ApplicationHistoryRepository repository;

    private final MutableLiveData<NetworkResult<ApplicationHistoryResponse>> applicationHistoryLiveData = new MutableLiveData<>();

    public ApplicationHistoryViewModel() {
        ApplicationHistoryApiService api = RetrofitClient.getRetrofit().create(ApplicationHistoryApiService.class);
        this.repository = new ApplicationHistoryRepository(api);
    }

    public LiveData<NetworkResult<ApplicationHistoryResponse>> getApplicationHistoryLiveData() {
        return applicationHistoryLiveData;
    }

    public void getApplicationHistory(int page, int limit, String search){
        repository.getApplicationHistory(page, limit, search, applicationHistoryLiveData::postValue);
    }
}