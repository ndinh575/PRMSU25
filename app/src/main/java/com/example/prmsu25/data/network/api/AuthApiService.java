package com.example.prmsu25.data.network.api;

import com.example.prmsu25.data.model.response.LoginResponse;
import com.example.prmsu25.data.model.response.RegisterResponse;
import com.example.prmsu25.data.model.response.VerifyEmailResponse;
import com.example.prmsu25.data.network.dto.LoginRequest;
import com.example.prmsu25.data.network.dto.RegisterRequest;
import com.example.prmsu25.data.network.dto.VerifyEmailRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/sign-in")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("auth/sign-up")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("auth/verify-email")
    Call<VerifyEmailResponse> verifyEmail(@Body VerifyEmailRequest request);
}
