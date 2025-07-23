package com.example.prmsu25.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.data.model.User;
import com.example.prmsu25.data.model.response.ForgotPasswordResponse;
import com.example.prmsu25.data.model.response.LoginResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.AuthApiService;
import com.example.prmsu25.data.network.dto.ForgotPasswordRequest;
import com.example.prmsu25.data.network.dto.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final AuthApiService apiService;

    public AuthRepository(AuthApiService apiService) {
        this.apiService = apiService;
    }

    public Call<LoginResponse> login(String email, String password) {
        return apiService.login(new LoginRequest(email, password));
    }

}


