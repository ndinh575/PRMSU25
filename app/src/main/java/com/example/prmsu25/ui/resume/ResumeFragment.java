package com.example.prmsu25.ui.resume;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmsu25.data.model.ResumeItem;

import com.example.prmsu25.R;

public class ResumeFragment extends Fragment {

    private ResumeViewModel mViewModel;
    private TextView fileNameTextView;
    private Button uploadButton;
    private RecyclerView resumeListRecyclerView;

    private Uri selectedFileUri;

    // Sử dụng ActivityResultLauncher hiện đại thay cho onActivityResult cũ
    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedFileUri = result.getData().getData();
                    String fileName = getFileName(selectedFileUri);
                    fileNameTextView.setText(fileName);
                    uploadButton.setEnabled(true);
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resume, container, false);

        fileNameTextView = view.findViewById(R.id.resume_file_name);
        Button selectFileButton = view.findViewById(R.id.button_select_file);
        uploadButton = view.findViewById(R.id.button_upload_file);
        uploadButton.setEnabled(false);

        selectFileButton.setOnClickListener(v -> openFilePicker());
        uploadButton.setOnClickListener(v -> {
            if (selectedFileUri != null) {
                mViewModel.uploadFile(selectedFileUri, requireContext());
            }
        });
        resumeListRecyclerView = view.findViewById(R.id.resume_list);
        resumeListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ResumeViewModel.class);
        mViewModel.getMyCVs().observe(getViewLifecycleOwner(), resumeItems -> {
            ResumeListAdapter adapter = new ResumeListAdapter(resumeItems, new ResumeListAdapter.OnActionListener() {
                @Override
                public void onViewClicked(ResumeItem item) {
                    // Mở CV bằng trình đọc PDF (WebView hoặc browser)
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(item.getFileUrl()), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }

                @Override
                public void onDeleteClicked(ResumeItem item) {
                    // Gợi ý: hiện dialog xác nhận sau này
                    Toast.makeText(requireContext(), "Delete chưa hỗ trợ (demo)", Toast.LENGTH_SHORT).show();
                }
            });
            resumeListRecyclerView.setAdapter(adapter);
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        filePickerLauncher.launch(Intent.createChooser(intent, "Select a file"));
    }

    private String getFileName(Uri uri) {
        Cursor cursor = requireActivity().getContentResolver()
                .query(uri, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            String result = cursor.getString(nameIndex);
            cursor.close();
            return result;
        }
        return "Unknown";
    }
}
