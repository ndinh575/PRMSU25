package com.example.prmsu25.data.network.api;

import com.example.prmsu25.data.model.response.LoginResponse;
import com.example.prmsu25.data.network.dto.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/sign-in")
    Call<LoginResponse> login(@Body LoginRequest request);
}
