package com.example.prmsu25.data.network.api;

import com.example.prmsu25.data.model.City;
import com.example.prmsu25.data.model.response.DistrictResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LocationApiService {
    @GET("api/p")
    Call<List<City>> getCities();

    @GET("api/p/{cityCode}")
    Call<DistrictResponse> getDistrictsByCityCode(
            @Path("cityCode") int cityCode,
            @Query("depth") int depth
    );
}
