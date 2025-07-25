package com.example.prmsu25.ui.resume;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.ResumeItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class ResumeViewModel extends ViewModel {
    private final MutableLiveData<List<ResumeItem>> myCVs = new MutableLiveData<>();

    public void uploadFile(Uri fileUri, Context context) {

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            byte[] fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);
            inputStream.close();

            Log.d("ResumeUpload", "File ready to upload. Size: " + fileBytes.length);

        } catch (IOException e) {
            Log.e("ResumeUpload", "Upload failed", e);
        }
    }

    public ResumeViewModel() {
        List<ResumeItem> fakeCVs = Arrays.asList(
                new ResumeItem("CV_Phúc_DevOps.pdf", "https://www.orimi.com/pdf-test.pdf", "2025-07-24"),
                new ResumeItem("CV_Backend_2025.pdf", "https://www.cte.iup.edu/cte/Resources/PDF_TestPage.pdf", "2025-06-15")
        );
        myCVs.setValue(fakeCVs);
    }

    public LiveData<List<ResumeItem>> getMyCVs() {
        return myCVs;
    }
}