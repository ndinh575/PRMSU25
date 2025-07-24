package com.example.prmsu25.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.data.model.User;
import com.example.prmsu25.data.model.response.ForgotPasswordResponse;
import com.example.prmsu25.data.model.response.LoginResponse;
import com.example.prmsu25.data.model.response.RegisterResponse;
import com.example.prmsu25.data.model.response.VerifyEmailResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.AuthApiService;
import com.example.prmsu25.data.network.dto.ForgotPasswordRequest;
import com.example.prmsu25.data.network.dto.LoginRequest;
import com.example.prmsu25.data.network.dto.RegisterRequest;
import com.example.prmsu25.data.network.dto.VerifyEmailRequest;

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
    public Call<RegisterResponse> register(String name, String email, String password, String accountType) {
        return apiService.register(new RegisterRequest(name, email, password, accountType));
    }

    public Call<VerifyEmailResponse> verifyEmail(String email, String verificationCode, String password) {
        return apiService.verifyEmail(new VerifyEmailRequest(email, verificationCode, password));
    }
}


