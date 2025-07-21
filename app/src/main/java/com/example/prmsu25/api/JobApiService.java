package com.example.prmsu25.api;

import com.example.prmsu25.model.response.JobDetailResponse;
import com.example.prmsu25.model.response.JobResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JobApiService {
    @GET("job") // vì base URL đã chứa /api/v1/
    Call<JobResponse> getJobs(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("location") String location
    );

    @GET("job/{id}")
    Call<JobDetailResponse> getJobDetail(@Path("id") String jobId);
}
