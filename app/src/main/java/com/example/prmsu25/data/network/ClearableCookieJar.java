package com.example.prmsu25.data.network;

import okhttp3.CookieJar;

public interface ClearableCookieJar extends CookieJar {
    void clear();
}
