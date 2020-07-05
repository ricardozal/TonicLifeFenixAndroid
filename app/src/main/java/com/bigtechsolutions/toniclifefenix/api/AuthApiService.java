package com.bigtechsolutions.toniclifefenix.api;

import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthApiService {

    @GET("products")
    Call<GenericResponse<List<Product>>> getProducts();

}
