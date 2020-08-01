package com.bigtechsolutions.toniclifefenix.api;

import com.bigtechsolutions.toniclifefenix.api.requests.AuthRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.SelectAddressRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.ValidateInvRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.Token;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApiService {


    /* PRODUCT APIs */
    @GET("products")
    Call<GenericResponse<List<Product>>> getProducts();

    @GET("product/{productId}/details")
    Call<GenericResponse<Product>> productDetails(@Path("productId") int productId);

    /* ADDRESS APIs */
    @GET("distributor/{distributorId}/addresses")
    Call<GenericResponse<List<Address>>> getAddresses(@Path("distributorId") int distributorId);

    @POST("distributor/select-address")
    Call<GenericResponse<List<Address>>> setSelectedAddress(@Body SelectAddressRequest request);

    /* BRANCHES APIs */
    @GET("all-branches")
    Call<GenericResponse<List<Branch>>> getBranches();

    /* ORDER SHOPPING CART */

    @GET("get-payment-methods")
    Call<GenericResponse<List<PaymentMethod>>> getPaymentMethods();

    @POST("validate-branch-inventory")
    Call<GenericResponse<Branch>> validateInventory(@Body ValidateInvRequest request);

    @GET("auth/logout")
    Call<GenericResponse<String>> logout();

}
