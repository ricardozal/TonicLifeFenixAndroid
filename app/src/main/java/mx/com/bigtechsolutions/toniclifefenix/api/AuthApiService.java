package mx.com.bigtechsolutions.toniclifefenix.api;

import mx.com.bigtechsolutions.toniclifefenix.api.requests.FirebaseTokenRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.GenerateIntentRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.GetCandidatesRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.NewDistributorRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.OrderRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.RegisterPointsRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.SaveOrderWithDistRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.SelectAddressRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.UpsertAddressRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.ValidateInvRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.ContentMobileResponse;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Order;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.OrderItem;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.OrderResponse;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Promotion;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.share_points.SharePointsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApiService {


    /* PRODUCT APIs */
    @GET("products/{countryId}/{productName}")
    Call<GenericResponse<List<Product>>> getProducts(@Path("countryId") int countryId, @Path("productName") String productName);

    @GET("product/{productId}/details")
    Call<GenericResponse<Product>> productDetails(@Path("productId") int productId);

    /* ADDRESS APIs */
    @GET("distributor/{distributorId}/addresses")
    Call<GenericResponse<List<Address>>> getAddresses(@Path("distributorId") int distributorId);

    @POST("distributor/select-address")
    Call<GenericResponse<List<Address>>> setSelectedAddress(@Body SelectAddressRequest request);

    @GET("distributor/{distributorId}/address/{addressId}/show")
    Call<GenericResponse<Address>> getAddress(@Path("addressId") int addressId, @Path("distributorId") int distributorId);

    @POST("distributor/{distributorId}/save-address")
    Call<GenericResponse<String>> saveAddress(@Path("distributorId") int distributorId, @Body UpsertAddressRequest request);

    /* BRANCHES APIs */
    @GET("distributor/{distributorId}/all-branches")
    Call<GenericResponse<List<Branch>>> getBranches(@Path("distributorId") int distributorId);

    /* ORDER SHOPPING CART */

    @GET("get-payment-methods")
    Call<GenericResponse<List<PaymentMethod>>> getPaymentMethods();

    @POST("distributor/{distributorId}/validate-branch-inventory")
    Call<GenericResponse<Branch>> validateInventory(@Body ValidateInvRequest request, @Path("distributorId") int distributorId);

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

    /* Save Token Firebase */

    @POST("distributor/{distributorId}/save-firebase-token")
    Call<GenericResponse<String>> saveFirebaseToken(@Path("distributorId") int distributorId, @Body FirebaseTokenRequest request);

    @GET("order/{orderId}/validate-register-points")
    Call<GenericResponse<String>> validateRegisterPoints(@Path("orderId") int orderId);

    @POST("distributor/register-points")
    Call<GenericResponse<String>> registerPoints(@Body RegisterPointsRequest request);

    /* Share Points */
    @POST("distributor/share-points")
    Call<SharePointsResponse> getCandidates(@Body GetCandidatesRequest request);

    @POST("save-order-external-points")
    Call<GenericResponse<OrderResponse>> saveOrderWithExternalPoints(@Body SaveOrderWithDistRequest request);

    @GET("content-app-mobile")
    Call<List<ContentMobileResponse>> getContentMobileApp();

}
