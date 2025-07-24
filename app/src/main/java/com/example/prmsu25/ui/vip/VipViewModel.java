package com.example.prmsu25.ui.vip;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.response.VietQRResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.repository.VipRepository;

public class VipViewModel extends ViewModel {

    private final VipRepository vipRepository;
    private final MutableLiveData<NetworkResult<VietQRResponse>> vietQRLiveData = new MutableLiveData<>();

    public VipViewModel() {
        vipRepository = new VipRepository();
    }

    public LiveData<NetworkResult<VietQRResponse>> getVietQRLiveData() {
        return vietQRLiveData;
    }

    public void createVietQR(int amount, String description) {
        // Khi bắt đầu tạo mã QR, đánh dấu trạng thái là "loading"
        vietQRLiveData.setValue(NetworkResult.loading());

        // Gọi repository để tạo mã QR
        vipRepository.createVietQR(amount, description, vietQRLiveData);
    }
}
