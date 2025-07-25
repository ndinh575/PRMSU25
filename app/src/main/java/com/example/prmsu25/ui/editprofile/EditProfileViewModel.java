package com.example.prmsu25.ui.editprofile;

import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmsu25.data.model.City;
import com.example.prmsu25.data.model.User;
import com.example.prmsu25.data.model.response.AvatarUploadResponse;
import com.example.prmsu25.data.model.response.DistrictResponse;
import com.example.prmsu25.data.model.response.ProfileResponse;
import com.example.prmsu25.data.network.LocationRetrofitClient;
import com.example.prmsu25.data.network.NetworkResult;
import com.example.prmsu25.data.network.RetrofitClient;
import com.example.prmsu25.data.network.api.LocationApiService;
import com.example.prmsu25.data.network.api.UserApiService;
import com.example.prmsu25.data.repository.EditProfileRepository;
import com.example.prmsu25.data.repository.LocationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class EditProfileViewModel extends ViewModel {

    private final EditProfileRepository editProfileRepository;

    private final LocationRepository locationRepository;

    private final MutableLiveData<NetworkResult<ProfileResponse>> updateProfileResult = new MutableLiveData<>();
    private final MutableLiveData<NetworkResult<AvatarUploadResponse>> uploadAvatarResult = new MutableLiveData<>();

    private final MutableLiveData<NetworkResult<List<City>>> citiesLiveData = new MutableLiveData<>();

    private final MutableLiveData<NetworkResult<DistrictResponse>> districtsLiveData = new MutableLiveData<>();

    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    private List<City> cachedCityList = new ArrayList<>();

    public EditProfileViewModel(){
        UserApiService userApiService = RetrofitClient.getRetrofit().create(UserApiService.class);
        LocationApiService locationApiService = LocationRetrofitClient.getRetrofit().create(LocationApiService.class);
        this.editProfileRepository = new EditProfileRepository(userApiService);
        this.locationRepository = new LocationRepository(locationApiService);
    }

    public LiveData<NetworkResult<ProfileResponse>> getUpdateProfileResult(){
        return updateProfileResult;
    }

    public LiveData<NetworkResult<AvatarUploadResponse>> getUploadAvatarResult(){
        return uploadAvatarResult;
    }

    public LiveData<NetworkResult<List<City>>> getCitiesLiveData() {
        return citiesLiveData;
    }

    public LiveData<NetworkResult<DistrictResponse>> getDistrictsLiveData() {
        return districtsLiveData;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void setCachedCityList(List<City> cityList) {
        this.cachedCityList = cityList;
    }

    public void updateUserProfile(Map<String, String> fields){
        editProfileRepository.updateUserProfile(fields, updateProfileResult::postValue);
    }

    public void uploadAvatar(MultipartBody.Part avatarPart){
        editProfileRepository.uploadAvatar(avatarPart, uploadAvatarResult::postValue);
    }

    public void fetchCities(){
        locationRepository.getCities(citiesLiveData::postValue);
    }

    public void fetchDistricts(int cityCode){
        locationRepository.getDistrict(districtsLiveData::postValue, cityCode);
    }

    public void setCurrentUser(User user) {
        currentUser.setValue(user);
    }

    public AdapterView.OnItemSelectedListener createCitySelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cachedCityList != null && position < cachedCityList.size()) {
                    City selectedCity = cachedCityList.get(position);
                    int cityCode = selectedCity.getCode();
                    fetchDistricts(cityCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }
}