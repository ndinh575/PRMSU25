package com.example.prmsu25.ui.jobdetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmsu25.databinding.FragmentJobDetailBinding;
import com.example.prmsu25.data.model.JobDetail;
import com.example.prmsu25.data.network.NetworkResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class JobDetailFragment extends Fragment {

    private FragmentJobDetailBinding binding;
    private JobDetailViewModel viewModel;
    private static final int PICK_CV_REQUEST = 1;
    private Uri resumeUri;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJobDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);

        String jobId = getArguments() != null ? getArguments().getString("jobId") : null;
        if (jobId != null) {
            observeJobDetail();
            viewModel.fetchJobDetail(jobId);
        }

        // Initialize apply button click listener
        Button applyButton = binding.btnApply;
        applyButton.setOnClickListener(v -> {
            if (resumeUri != null) {
                applyForJob(jobId, resumeUri);
            } else {
                Toast.makeText(getContext(), "Please select a CV file", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle file picker for selecting CV
        binding.btnUploadCV.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("application/pdf|application/msword|application/vnd.openxmlformats-officedocument.wordprocessingml.document|image/*");  // Cho phép chọn PDF, DOC, DOCX, JPG, PNG
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);  // Chỉ cho phép chọn 1 file
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);  // Cấp quyền đọc cho file đã chọn
            startActivityForResult(intent, PICK_CV_REQUEST);
        });
    }

    private void observeJobDetail() {
        viewModel.getJobDetailLiveData().observe(getViewLifecycleOwner(), result -> {
            switch (result.getStatus()) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.scrollContent.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.scrollContent.setVisibility(View.VISIBLE);
                    bindJobData(result.getData());
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.scrollContent.setVisibility(View.GONE);
                    binding.tvError.setVisibility(View.VISIBLE);
                    binding.tvError.setText(result.getMessage());
                    break;
            }
        });
    }

    private void bindJobData(JobDetail job) {
        // Set the job data directly to the corresponding views
        binding.tvTitle.setText(job.getTitle());
        binding.tvDescription.setText(job.getDescription());
        binding.tvRequirements.setText(TextUtils.isEmpty(job.getRequirements()) ? "Không yêu cầu" : job.getRequirements());

        // Direct binding for other job details
        binding.tvSalary.setText(job.getSalary());
        binding.tvExperience.setText(TextUtils.isEmpty(job.getExperience()) ? "Không yêu cầu" : job.getExperience());
        binding.tvQuantity.setText(String.valueOf(job.getQuantity()));
        binding.tvLevel.setText(job.getLevel());
        binding.tvIndustry.setText(job.getIndustry());

        binding.tvLocation.setText(job.getLocation());
        binding.tvDeadline.setText(formatDate(job.getDeadline()));
        binding.tvCreatedAt.setText(formatDate(job.getCreatedAt()));
    }

    private String formatDate(String isoDate) {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = isoFormat.parse(isoDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            return outputFormat.format(date);
        } catch (Exception e) {
            return isoDate; // Return the original if there's an error
        }
    }

    private void applyForJob(String jobId, Uri resumeUri) {
        try {
            // Lấy InputStream từ URI
            InputStream inputStream = requireContext().getContentResolver().openInputStream(resumeUri);

            // Tạo RequestBody từ InputStream
            RequestBody requestFile = RequestBody.create(
                    MediaType.parse(requireContext().getContentResolver().getType(resumeUri)),
                    inputStream.toString()
            );

            // Tạo MultipartBody.Part từ RequestBody
            MultipartBody.Part body = MultipartBody.Part.createFormData("resumeFile", getFileNameFromUri(resumeUri), requestFile);

            // Thêm applicantId vào request body (giả sử đã có applicantId)
            RequestBody applicantId = RequestBody.create(MediaType.parse("text/plain"), "userId");

            // Gọi viewModel để nộp đơn
            viewModel.applyForJob(jobId, body, applicantId);

            viewModel.getApplicationLiveData().observe(getViewLifecycleOwner(), result -> {
                switch (result.getStatus()) {
                    case LOADING:
                        binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Application submitted successfully", Toast.LENGTH_SHORT).show();
                        break;
                    case ERROR:
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Failed to apply for job", Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CV_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri selectedUri = data.getData();

                // Kiểm tra loại file đã chọn (PDF, DOCX)
                String mimeType = getContext().getContentResolver().getType(selectedUri);
                if (mimeType != null && (mimeType.equals("application/pdf") || mimeType.equals("application/msword") || mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
                    resumeUri = selectedUri;
                    binding.tvSelectedFile.setText("CV selected: " + resumeUri.getLastPathSegment());
                } else {
                    Toast.makeText(getContext(), "Invalid file type. Please select a PDF or DOC file.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (columnIndex != -1) {
                fileName = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return fileName;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up binding
    }
}
