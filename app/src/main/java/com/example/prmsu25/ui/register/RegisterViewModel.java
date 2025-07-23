package com.example.prmsu25.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.response.LoginResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.AuthApiService;
import com.example.prmsu25.data.repository.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private final MutableLiveData<NetworkResult<LoginResponse>> registerResult = new MutableLiveData<>();
    private final AuthRepository repository;

    public RegisterViewModel() {
        AuthApiService api = RetrofitClient.getRetrofit().create(AuthApiService.class);
        repository = new AuthRepository(api);
    }

    public LiveData<NetworkResult<LoginResponse>> getRegisterResult() {
        return registerResult;
    }

    public void register(String email, String password) {
        /*registerResult.setValue(NetworkResult.loading());

        repository.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registerResult.postValue(NetworkResult.success(response.body()));
                } else {
                    registerResult.postValue(NetworkResult.error("Login failed"));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                registerResult.postValue(NetworkResult.error(t.getMessage()));
            }
        });
        */
    }

    public void resetRegisterResult() {
        registerResult.setValue(null);
    }
}