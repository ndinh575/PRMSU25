package com.example.prmsu25.ui.register;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.response.RegisterResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.AuthApiService;
import com.example.prmsu25.data.repository.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private final MutableLiveData<NetworkResult<RegisterResponse>> registerResult = new MutableLiveData<>();
    private final AuthRepository repository;

    public RegisterViewModel() {
        AuthApiService api = RetrofitClient.getRetrofit().create(AuthApiService.class);
        repository = new AuthRepository(api);
    }

    public LiveData<NetworkResult<RegisterResponse>> getRegisterResult() {
        return registerResult;
    }

    public void register(String name, String email, String password) {
        registerResult.setValue(NetworkResult.loading());

        String accountType = com.example.prmsu25.data.model.enums.AccountType.APPLICANT.getValue();

        repository.register(name, email, password, accountType).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registerResult.postValue(NetworkResult.success(response.body()));
                } else {
                    // TODO: Parse the actual error message from the response body
                    registerResult.postValue(NetworkResult.error("Registration failed. User may already exist."));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                registerResult.postValue(NetworkResult.error(t.getMessage()));
            }
        });

    }

    public void resetRegisterResult() {
        registerResult.setValue(null);
    }
}