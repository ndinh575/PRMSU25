package com.example.prmsu25.ui.verifyemail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.response.VerifyEmailResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.AuthApiService;
import com.example.prmsu25.data.repository.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyEmailViewModel extends ViewModel {
    private final MutableLiveData<NetworkResult<VerifyEmailResponse>> verifyResult = new MutableLiveData<>();
    private final AuthRepository repository;

    public LiveData<NetworkResult<VerifyEmailResponse>> getVerifyResult() {
        return verifyResult;
    }

    public VerifyEmailViewModel() {
        AuthApiService api = RetrofitClient.getRetrofit().create(AuthApiService.class);
        repository = new AuthRepository(api);
    }

    public void verifyEmail(String email, String code, String password) {
        verifyResult.setValue(NetworkResult.loading());
        repository.verifyEmail(email, code, password).enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    verifyResult.postValue(NetworkResult.success(response.body()));
                } else {
                    verifyResult.postValue(NetworkResult.error("Verification failed. Invalid code."));
                }
            }

            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {
                verifyResult.postValue(NetworkResult.error(t.getMessage()));
            }
        });
    }

    public void resetVerifyResult() {
        verifyResult.setValue(null);
    }

}
