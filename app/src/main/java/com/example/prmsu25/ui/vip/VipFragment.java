package com.example.prmsu25.ui.vip;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmsu25.R;
import com.example.prmsu25.databinding.FragmentVipBinding;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.ui.vip.VipViewModel;

import com.squareup.picasso.Picasso;

public class VipFragment extends Fragment {

    private FragmentVipBinding binding;
    private VipViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVipBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(VipViewModel.class);

        // Bấm nút tạo mã QR
        binding.btnCreateVietQR.setOnClickListener(v -> {
            String points = binding.etPoints.getText().toString().trim();

            // Kiểm tra dữ liệu đầu vào
            if (TextUtils.isEmpty(points)) {
                Toast.makeText(getContext(), "Vui lòng nhập số điểm cần nạp", Toast.LENGTH_SHORT).show();
                return;
            }

            int amount = Integer.parseInt(points) * 1000; // 1 điểm = 1000 VND
            String description = "Nạp " + points + " W4U";

            // Gọi API tạo mã QR
            viewModel.createVietQR(amount, description);
        });

        // Quan sát sự thay đổi trong kết quả tạo mã QR
        viewModel.getVietQRLiveData().observe(getViewLifecycleOwner(), result -> {
            switch (result.getStatus()) {
                case LOADING:
                    // Hiển thị progress bar nếu cần
                    break;
                case SUCCESS:
                    // Hiển thị mã QR bằng Picasso
                    String qrCodeUrl = result.getData().getQrCode();
                    Log.d("TAG", "tạo dc mã: " + qrCodeUrl);
                    binding.ivQRCode.setVisibility(View.VISIBLE);

                    // Sử dụng Picasso để tải và hiển thị mã QR
                    Picasso.get()
                            .load(qrCodeUrl)  // Đảm bảo rằng qrCodeUrl là URL hợp lệ của mã QR
                            .placeholder(R.drawable.placholder)  // Hiển thị ảnh placeholder khi đang tải
                            .error(R.drawable.error_image)  // Hiển thị ảnh lỗi nếu tải thất bại
                            .into(binding.ivQRCode);  // Đảm bảo rằng binding.ivQRCode là ImageView của bạn
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Không thể tạo mã QR", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}
