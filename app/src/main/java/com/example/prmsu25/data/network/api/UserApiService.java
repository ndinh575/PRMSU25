package com.example.prmsu25.data.network.api;

import com.example.prmsu25.data.model.response.AvatarUploadResponse;
import com.example.prmsu25.data.model.response.ProfileResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApiService {
    @GET("user/profile")
    Call<ProfileResponse> getUserProfile();

    @PATCH("user/profile")
    Call<ProfileResponse> updateUserProfile(@Body Map<String, String> updatedFields);

    @Multipart
    @POST("upload_avatar")
    Call<AvatarUploadResponse> uploadAvatar(@Part MultipartBody.Part avatarFile);
}
