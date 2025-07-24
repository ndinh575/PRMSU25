package com.example.prmsu25.data.network.api;


import com.example.prmsu25.data.model.Job;
import com.example.prmsu25.data.model.response.ApplicationResponse;
import com.example.prmsu25.data.model.response.JobDetailResponse;
import com.example.prmsu25.data.model.response.JobResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @Multipart
    @POST("application/apply/{jobId}")
    Call<ApplicationResponse> applyJob(
            @Path("jobId") String jobId,
            @Part MultipartBody.Part resumeFile,
            @Part("applicantId") RequestBody applicantId
    );

    @GET("job/recommended")
    Call<JobResponse> getRecommendedJobs();

    @GET("job/ai-recommended")
    Call<JobResponse> getAIRecommendedJobs();
}
