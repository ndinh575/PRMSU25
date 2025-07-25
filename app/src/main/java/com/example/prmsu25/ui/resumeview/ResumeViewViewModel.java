package com.example.prmsu25.ui.resumeview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.ResumeApplicationItem;

import java.util.Arrays;
import java.util.List;

public class ResumeViewViewModel extends ViewModel {

    private final MutableLiveData<List<ResumeApplicationItem>> applicationCVs = new MutableLiveData<>();

    public ResumeViewViewModel() {
        loadMockData();
    }

    private void loadMockData() {
        List<ResumeApplicationItem> fakeCVs = Arrays.asList(
                new ResumeApplicationItem("Nguyễn Văn A", "DevOps Engineer", "CV_DevOps_NguyenA.pdf",
                        "https://www.orimi.com/pdf-test.pdf", "2025-07-24"),
                new ResumeApplicationItem("Nguyễn Văn A", "Java Backend", "CV_TranThiB_Backend.pdf",
                        "https://www.cte.iup.edu/cte/Resources/PDF_TestPage.pdf", "2025-07-21")
        );
        applicationCVs.setValue(fakeCVs);
    }

    public LiveData<List<ResumeApplicationItem>> getApplicationCVs() {
        return applicationCVs;
    }
}