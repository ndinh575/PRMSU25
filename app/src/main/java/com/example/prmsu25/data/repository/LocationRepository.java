package com.example.prmsu25.data.repository;

import android.util.Log;

import com.example.prmsu25.data.model.City;
import com.example.prmsu25.data.model.response.DistrictResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.api.LocationApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepository {

    private final LocationApiService apiService;

    public LocationRepository(LocationApiService apiService){
        this.apiService = apiService;
    }

    public void getCities(RepositoryCallback<List<City>> callback){
        callback.onResult(NetworkResult.loading());

        apiService.getCities().enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if(response.isSuccessful() && response.body() != null){
                    callback.onResult(NetworkResult.success(response.body()));
                    Log.d("TAG", "OK");
                } else {
                    callback.onResult(NetworkResult.error("Error"));
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }

    public void getDistrict(RepositoryCallback<DistrictResponse> callback, int cityCode){
        callback.onResult(NetworkResult.loading());

        apiService.getDistrictsByCityCode(cityCode, 2).enqueue(new Callback<DistrictResponse>() {
            @Override
            public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Error"));
                }
            }

            @Override
            public void onFailure(Call<DistrictResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }
}
