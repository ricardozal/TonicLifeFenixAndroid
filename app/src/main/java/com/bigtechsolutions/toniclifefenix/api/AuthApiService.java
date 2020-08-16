package com.bigtechsolutions.toniclifefenix.api;

import com.bigtechsolutions.toniclifefenix.api.requests.AuthRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.GenerateIntentRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.NewDistributorRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.OrderRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.SelectAddressRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.UpsertAddressRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.ValidateInvRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.Token;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Order;
import com.bigtechsolutions.toniclifefenix.api.responses.models.OrderItem;
import com.bigtechsolutions.toniclifefenix.api.responses.models.OrderResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Promotion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApiService {


    /* PRODUCT APIs */
    @GET("products/{countryId}")
    Call<GenericResponse<List<Product>>> getProducts(@Path("countryId") int countryId);

    @GET("product/{productId}/details")
    Call<GenericResponse<Product>> productDetails(@Path("productId") int productId);

    /* ADDRESS APIs */
    @GET("distributor/{distributorId}/addresses")
    Call<GenericResponse<List<Address>>> getAddresses(@Path("distributorId") int distributorId);

    @POST("distributor/select-address")
    Call<GenericResponse<List<Address>>> setSelectedAddress(@Body SelectAddressRequest request);

    @GET("distributor/{addressId}/address")
    Call<GenericResponse<Address>> getAddress(@Path("addressId") int addressId);

    @POST("distributor/save-address")
    Call<GenericResponse<String>> saveAddress(@Body UpsertAddressRequest request);

    /* BRANCHES APIs */
    @GET("all-branches")
    Call<GenericResponse<List<Branch>>> getBranches();

    /* ORDER SHOPPING CART */

    @GET("get-payment-methods")
    Call<GenericResponse<List<PaymentMethod>>> getPaymentMethods();

    @POST("validate-branch-inventory")
    Call<GenericResponse<Branch>> validateInventory(@Body ValidateInvRequest request);

    @POST("generate-intent")
    Call<GenericResponse<String>> generateIntent(@Body GenerateIntentRequest request);

    @POST("save-order")
    Call<GenericResponse<OrderResponse>> saveOrder(@Body OrderRequest request);

    /* New Distributor */
    @POST("save-new-distributor")
    Call<GenericResponse<String>> saveNewDistributor(@Body NewDistributorRequest request);

    @GET("auth/logout")
    Call<GenericResponse<String>> logout();

    /* Orders */

    @GET("distributor/{distributorId}/orders")
    Call<GenericResponse<List<OrderItem>>> getOrders(@Path("distributorId") int distributorId);

    @GET("order/{orderId}/show")
    Call<GenericResponse<Order>> getOrder(@Path("orderId") int orderId);

    /* Promotions */
    @GET("distributor/{distributorId}/promotions")
    Call<GenericResponse<List<Promotion>>> getPromotions(@Path("distributorId") int distributorId);

}
