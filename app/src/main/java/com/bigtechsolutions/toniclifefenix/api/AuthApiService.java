package com.bigtechsolutions.toniclifefenix.api;

import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApiService {


    /* PRODUCT APIs */
    @GET("products")
    Call<GenericResponse<List<Product>>> getProducts();

    @GET("product/{productId}/details")
    Call<GenericResponse<Product>> productDetails(@Path("productId") int productId);

    @GET("auth/logout")
    Call<GenericResponse<String>> logout();

}
