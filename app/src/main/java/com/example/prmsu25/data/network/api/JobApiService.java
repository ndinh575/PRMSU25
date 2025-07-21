package com.example.prmsu25.data.network.api;


import com.example.prmsu25.data.model.Job;
import com.example.prmsu25.data.model.response.JobDetailResponse;
import com.example.prmsu25.data.model.response.JobResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JobApiService {
    @GET("job")
    Call<JobResponse> getJobs(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("keywords") String keywords
    );
    @GET("job/{id}")
    Call<JobDetailResponse> getJobDetail(@Path("id") String jobId);
}
