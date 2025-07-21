package com.example.prmsu25.ui.jobdetail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prmsu25.R;
import com.example.prmsu25.api.JobApiService;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.model.JobDetail;
import com.example.prmsu25.model.response.JobDetailResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailFragment extends Fragment {

    private static final SimpleDateFormat ISO_DATE =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    private static final SimpleDateFormat OUT_DATE =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private TextView tvJobTitle, tvJobDescription, tvApplyInstruction, tvDeadline;
    private Button btnApply, btnSave;

    // Card info
    private TextView tvProjectId, tvProjectCreated, tvProjectLocation, tvProjectBudget,
            tvProjectWorkType, tvProjectPayment;
    private TextView tvCustomerName, tvCustomerJoin, tvCustomerPosted;
    private ImageView imgCustomer;
    private ProgressBar pbLoading;

    private JobApiService apiService;
    private String jobId;

    public JobDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_job_detail, container, false);

        // Bind UI
        tvJobTitle = v.findViewById(R.id.tvJobTitle);
        tvJobDescription = v.findViewById(R.id.tvJobDescription);
        tvApplyInstruction = v.findViewById(R.id.tvApplyInstruction);
        tvDeadline = v.findViewById(R.id.tvDeadline);
        btnApply = v.findViewById(R.id.btnApply);
        btnSave = v.findViewById(R.id.btnSave);

        tvProjectId = v.findViewById(R.id.tvProjectId);
        tvProjectCreated = v.findViewById(R.id.tvProjectCreated);
        tvProjectLocation = v.findViewById(R.id.tvProjectLocation);
        tvProjectBudget = v.findViewById(R.id.tvProjectBudget);
        tvProjectWorkType = v.findViewById(R.id.tvProjectWorkType);
        tvProjectPayment = v.findViewById(R.id.tvProjectPayment);

        tvCustomerName = v.findViewById(R.id.tvCustomerName);
        tvCustomerJoin = v.findViewById(R.id.tvCustomerJoin);
        tvCustomerPosted = v.findViewById(R.id.tvCustomerPosted);
        imgCustomer = v.findViewById(R.id.imgCustomer);

        pbLoading = v.findViewById(R.id.pbLoadingDetail);

        // Lấy jobId từ args
        if (getArguments() != null) {
            jobId = getArguments().getString("jobId");
        }

        // Retrofit
        apiService = RetrofitClient.getRetrofit().create(JobApiService.class);

        // Load data
        if (!TextUtils.isEmpty(jobId)) {
            loadJobDetail(jobId);
        } else {
            Toast.makeText(getContext(), "Thiếu mã job", Toast.LENGTH_SHORT).show();
        }

        // TODO: xử lý nút ứng tuyển & lưu tin
        btnApply.setOnClickListener(v1 ->
                Toast.makeText(getContext(), "Ứng tuyển: " + jobId, Toast.LENGTH_SHORT).show());

        btnSave.setOnClickListener(v12 ->
                Toast.makeText(getContext(), "Lưu tin: " + jobId, Toast.LENGTH_SHORT).show());

        return v;
    }

    private void loadJobDetail(String id) {
        pbLoading.setVisibility(View.VISIBLE);
        apiService.getJobDetail(id).enqueue(new Callback<JobDetailResponse>() {
            @Override
            public void onResponse(Call<JobDetailResponse> call, Response<JobDetailResponse> response) {
                pbLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    bindData(response.body().data);
                } else {
                    Toast.makeText(getContext(), "Không tải được chi tiết", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobDetailResponse> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindData(JobDetail job) {
        // LEFT
        tvJobTitle.setText(job.title);
        tvJobDescription.setText(job.description);
        tvDeadline.setText("Hạn nộp hồ sơ: " + formatDate(job.deadline));

        // RIGHT card
        tvProjectId.setText("ID dự án: " + job.id);
        tvProjectCreated.setText("Ngày đăng: " + formatDate(job.createdAt));
        tvProjectLocation.setText("Địa điểm: " + safe(job.location));
        tvProjectBudget.setText("Ngân sách: " + safe(job.salary));
        tvProjectWorkType.setText("Hình thức làm việc: " + safe(job.deliveryTime));
        tvProjectPayment.setText("Hình thức trả lương: " + safe(job.priorityLevel));

        tvCustomerName.setText(safe(job.employerName));
        // Chưa có ngày tham gia/đăng bao nhiêu việc trên API → bạn tuỳ ghi chú
        tvCustomerJoin.setText("Tham gia: (chưa có dữ liệu)");
        tvCustomerPosted.setText("Đã đăng: (chưa có dữ liệu)");

        // Hình đại diện placeholder
        imgCustomer.setImageResource(R.drawable.ic_person);
    }

    private String safe(String v) {
        return v == null ? "-" : v;
    }

    private String formatDate(String iso) {
        if (iso == null) return "-";
        try {
            Date d = ISO_DATE.parse(iso);
            return OUT_DATE.format(d);
        } catch (ParseException e) {
            // Nếu backend trả định dạng khác: cứ hiển thị raw
            return iso;
        }
    }
}
