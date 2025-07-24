package com.example.prmsu25.data.network.api;

import com.example.prmsu25.data.model.VietQRRequest;
import com.example.prmsu25.data.model.response.VietQRResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface PaymentApiService {

    @POST("payment/create-vietqr")
    Call<VietQRResponse> createVietQR(@Body VietQRRequest request);
}
