package com.example.prmsu25.data.repository;

import com.example.prmsu25.data.model.response.AvatarUploadResponse;
import com.example.prmsu25.data.model.response.ProfileResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.api.UserApiService;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileRepository {

    private final UserApiService apiService;

    public EditProfileRepository(UserApiService apiService){
        this.apiService = apiService;
    }

    public void updateUserProfile(Map<String, String> fields, RepositoryCallback<ProfileResponse> callback){
        callback.onResult(NetworkResult.loading());

        apiService.updateUserProfile(fields).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Update profile failed"));
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }

    public void uploadAvatar(MultipartBody.Part avatar, RepositoryCallback<AvatarUploadResponse> callback){
        callback.onResult(NetworkResult.loading());

        apiService.uploadAvatar(avatar).enqueue(new Callback<AvatarUploadResponse>() {
            @Override
            public void onResponse(Call<AvatarUploadResponse> call, Response<AvatarUploadResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    callback.onResult(NetworkResult.success(response.body()));
                } else {
                    callback.onResult(NetworkResult.error("Upload avatar failed"));
                }
            }

            @Override
            public void onFailure(Call<AvatarUploadResponse> call, Throwable t) {
                callback.onResult(NetworkResult.error(t.getMessage()));
            }
        });
    }
}
