package com.bigtechsolutions.toniclifefenix.api;

import com.bigtechsolutions.toniclifefenix.commons.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthApiClient {

    private static AuthApiClient instance = null;
    private AuthApiService apiAuthService;
    private Retrofit retrofit;

    public AuthApiClient()
    {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new AuthInterceptor());
        OkHttpClient client = okHttpClientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_TONIC_LIFE_FENIX_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiAuthService = retrofit.create(AuthApiService.class);
    }

    public static AuthApiClient getInstance()
    {
        if (instance == null)
        {
            instance = new AuthApiClient();
        }

        return instance;
    }

    public AuthApiService getAuthApiService()
    {
        return apiAuthService;
    }

}