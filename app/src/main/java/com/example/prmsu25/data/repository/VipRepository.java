package com.example.prmsu25.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.prmsu25.data.model.VietQRRequest;
import com.example.prmsu25.data.network.api.PaymentApiService;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.model.response.VietQRResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VipRepository {

    private final PaymentApiService paymentApiService;

    public VipRepository() {
        paymentApiService = RetrofitClient.getRetrofit().create(PaymentApiService.class);
    }

    // Hàm tạo mã QR, truyền vào LiveData để cập nhật kết quả
    public void createVietQR(int amount, String description, MutableLiveData<NetworkResult<VietQRResponse>> vietQRLiveData) {
        VietQRRequest request = new VietQRRequest(amount, description);

        paymentApiService.createVietQR(request).enqueue(new Callback<VietQRResponse>() {
            @Override
            public void onResponse(Call<VietQRResponse> call, Response<VietQRResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Nếu thành công, cập nhật LiveData với kết quả
                    vietQRLiveData.setValue(NetworkResult.success(response.body()));
                    Log.d("TAG", "onResponse: "+response.body());
                } else {
                    // Nếu không thành công, trả về lỗi
                    vietQRLiveData.setValue(NetworkResult.error("Error creating QR code"));
                }
            }

            @Override
            public void onFailure(Call<VietQRResponse> call, Throwable t) {
                // Nếu thất bại khi gọi API, trả về lỗi
                vietQRLiveData.setValue(NetworkResult.error(t.getMessage()));
            }
        });
    }
}
