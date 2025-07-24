package com.example.prmsu25.data.model.response;

public class VietQRResponse {
    private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    @Override
    public String toString() {
        return "VietQRResponse{" +
                "qrCode='" + qrCode + '\'' +
                '}';
    }
}
