package com.example.prmsu25.ui.forgotpassword;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.response.ForgotPasswordResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.AuthApiService;
import com.example.prmsu25.data.network.dto.ForgotPasswordRequest;
import com.example.prmsu25.data.repository.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordViewModel extends ViewModel {
    private final MutableLiveData<NetworkResult<ForgotPasswordResponse>> forgotResult = new MutableLiveData<>();
    private final AuthApiService api;

    public ForgotPasswordViewModel() {
        api = RetrofitClient.getRetrofit().create(AuthApiService.class);
    }

    public LiveData<NetworkResult<ForgotPasswordResponse>> getForgotResult() {
        return forgotResult;
    }

    public void forgotPassword(String email) {
        forgotResult.setValue(NetworkResult.loading());

        ForgotPasswordRequest request = new ForgotPasswordRequest(email);

        api.forgotPassword(request).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    forgotResult.postValue(NetworkResult.success(response.body()));
                } else {
                    forgotResult.postValue(NetworkResult.error("Forgot password failed: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                forgotResult.postValue(NetworkResult.error("Network error: " + t.getMessage()));
            }
        });
    }

    public void resetForgotResult() {
        forgotResult.setValue(null);
    }
}