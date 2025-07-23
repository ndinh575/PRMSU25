package com.example.prmsu25.ui.login;

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

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<NetworkResult<LoginResponse>> loginResult = new MutableLiveData<>();
    private final AuthRepository repository;

    public LoginViewModel() {
        AuthApiService api = RetrofitClient.getRetrofit().create(AuthApiService.class);
        repository = new AuthRepository(api);
    }

    public LiveData<NetworkResult<LoginResponse>> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        loginResult.setValue(NetworkResult.loading());

        repository.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginResult.postValue(NetworkResult.success(response.body()));
                } else {
                    loginResult.postValue(NetworkResult.error("Login failed"));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResult.postValue(NetworkResult.error(t.getMessage()));
            }
        });
    }

    public void resetLoginResult() {
        loginResult.setValue(null);
    }
}
