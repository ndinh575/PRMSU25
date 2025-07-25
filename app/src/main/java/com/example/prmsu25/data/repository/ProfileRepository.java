package com.example.prmsu25.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.data.model.User;
import com.example.prmsu25.data.model.response.ProfileResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.api.UserApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    private final UserApiService apiService;

    public ProfileRepository(UserApiService apiService){
        this.apiService = apiService;
    }

    public void getProfile(RepositoryCallback<ProfileResponse> callback) {

        callback.onResult(NetworkResult.loading());

        apiService.getUserProfile().enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().isSuccess()){
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Error"));
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });

    }
}
