package com.example.prmsu25.data.network.api;

import com.example.prmsu25.data.model.response.ApplicationHistoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApplicationHistoryApiService {
    @GET("application/applied-jobs")
    Call<ApplicationHistoryResponse> getAppliedJobs(@Query("page") int page,
                                                    @Query("limit") int limit,
                                                    @Query("search") String search);
}
