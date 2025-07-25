package com.example.prmsu25.data.repository;

import com.example.prmsu25.data.network.NetworkResult;

public interface RepositoryCallback<T> {

    void onResult(NetworkResult<T> result);
}
