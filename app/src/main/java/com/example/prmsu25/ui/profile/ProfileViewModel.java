package com.example.prmsu25.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.User;
import com.example.prmsu25.data.model.response.ProfileResponse;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.UserApiService;
import com.example.prmsu25.data.repository.ProfileRepository;

public class ProfileViewModel extends ViewModel {

    private final ProfileRepository repository;

    private final MutableLiveData<NetworkResult<ProfileResponse>> profileLiveData = new MutableLiveData<>();

    public ProfileViewModel(){
        UserApiService api = RetrofitClient.getRetrofit().create(UserApiService.class);
        this.repository = new ProfileRepository(api);
    }

    public LiveData<NetworkResult<ProfileResponse>> getProfileLiveData(){
        return profileLiveData;
    }

    public void fetchUserProfile(){
        repository.getProfile(profileLiveData::postValue);
    }

}