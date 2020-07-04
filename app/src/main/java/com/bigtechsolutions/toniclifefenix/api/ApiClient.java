package com.bigtechsolutions.toniclifefenix.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiClient instance = null;
    private ApiService apiService;
    private Retrofit retrofit;

    public ApiClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
