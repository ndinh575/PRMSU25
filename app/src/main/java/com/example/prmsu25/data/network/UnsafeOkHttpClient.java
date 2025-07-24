package com.example.prmsu25.data.network;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class UnsafeOkHttpClient {

    private static OkHttpClient instance;
    private static InMemoryCookieJar cookieJar;

    public static OkHttpClient getInstance() {
        if (instance == null) {
            try {
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[]{};
                            }
                        }
                };

                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                cookieJar = new InMemoryCookieJar();

                instance = new OkHttpClient.Builder()
                        .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                        .hostnameVerifier((hostname, session) -> true)
                        .cookieJar(cookieJar)
                        .build();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public static void clearCookies() {
        if (cookieJar != null) {
            cookieJar.clear();
        }
    }
}
