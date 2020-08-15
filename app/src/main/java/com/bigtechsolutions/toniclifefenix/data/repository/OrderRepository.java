package com.bigtechsolutions.toniclifefenix.data.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.GenerateIntentRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.OrderRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.ValidateInvRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Order;
import com.bigtechsolutions.toniclifefenix.api.responses.models.OrderItem;
import com.bigtechsolutions.toniclifefenix.api.responses.models.OrderResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnOrderItemResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnOrderResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnSuccess;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    AuthApiService authApiService;
    AuthApiClient authApiClient;
    MutableLiveData<List<PaymentMethod>> paymentMethods;
    MutableLiveData<List<OrderItem>> orderItems;
    private final MutableLiveData<Boolean> downloadFinished = new MutableLiveData<>();


    public OrderRepository() {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
        paymentMethods = getPaymentMethods();
    }

    public MutableLiveData<List<OrderItem>> getOrders(){

        if(orderItems == null){

            orderItems = new MutableLiveData<>();

        }

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        Call<GenericResponse<List<OrderItem>>> call = authApiService.getOrders(distributorId);

        call.enqueue(new Callback<GenericResponse<List<OrderItem>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<OrderItem>>> call, Response<GenericResponse<List<OrderItem>>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        orderItems.setValue(response.body().getData());
                        setDownloadFinished();
                    }
                } else {
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<OrderItem>>> call, Throwable t) {
                Toast.makeText(MyFenixApp.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return orderItems;

    }

    public void getOrder(int orderId, OnOrderItemResponse onOrderItemResponse){

        Call<GenericResponse<Order>> call = authApiService.getOrder(orderId);

        call.enqueue(new Callback<GenericResponse<Order>>() {
            @Override
            public void onResponse(Call<GenericResponse<Order>> call, Response<GenericResponse<Order>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        onOrderItemResponse.OnSuccess("Bien", response.body().getMessage(), response.body().getData());
                    }
                } else {
                    onOrderItemResponse.OnError("Algo salió mal", "Error en el servidor");
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Order>> call, Throwable t) {
                onOrderItemResponse.OnError("Algo salió mal", "Error de conexión");
            }
        });

    }

    public MutableLiveData<List<PaymentMethod>> getPaymentMethods(){

        if(paymentMethods == null){

            paymentMethods = new MutableLiveData<>();

        }

        Call<GenericResponse<List<PaymentMethod>>> call = authApiService.getPaymentMethods();

        call.enqueue(new Callback<GenericResponse<List<PaymentMethod>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<PaymentMethod>>> call, Response<GenericResponse<List<PaymentMethod>>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        paymentMethods.setValue(response.body().getData());
                        setDownloadFinished();
                    }
                } else {
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<PaymentMethod>>> call, Throwable t) {
                Toast.makeText(MyFenixApp.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return paymentMethods;

    }

    public void validateInventory(ValidateInvRequest validateInvRequest, OnResponse onResponse){

        Call<GenericResponse<Branch>> call = authApiService.validateInventory(validateInvRequest);

        call.enqueue(new Callback<GenericResponse<Branch>>() {
            @Override
            public void onResponse(Call<GenericResponse<Branch>> call, Response<GenericResponse<Branch>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        SharedPreferencesManager.setIntegerValue(Constants.BRANCH_ID, response.body().getData().getId());
                        setDownloadFinished();
                        onResponse.OnSuccess("Todo bien", "Continuar");

                    }else {
                        setDownloadFinished();
                        onResponse.OnError("Atención", response.body().getMessage());
                    }
                } else {
                    onResponse.OnError("Atención", response.message());
                    setDownloadFinished();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<Branch>> call, Throwable t) {
                onResponse.OnError("Atención", t.getMessage());
                setDownloadFinished();
            }
        });

    }

    public void generateIntent(GenerateIntentRequest generateIntentRequest, OnSuccess onSuccess){

        Call<GenericResponse<String>> call = authApiService.generateIntent(generateIntentRequest);

        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {

                        setDownloadFinished();
                        onSuccess.OnRequestStripeSuccess(response.body().getData());

                    }else {

                        Toast.makeText(MyFenixApp.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        setDownloadFinished();

                    }
                } else {
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    setDownloadFinished();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {

                Toast.makeText(MyFenixApp.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                setDownloadFinished();

            }
        });

    }

    public void saveOrder(OrderRequest orderRequest, OnOrderResponse onOrderResponse){

        Call<GenericResponse<OrderResponse>> call = authApiService.saveOrder(orderRequest);

        call.enqueue(new Callback<GenericResponse<OrderResponse>>() {
            @Override
            public void onResponse(Call<GenericResponse<OrderResponse>> call, Response<GenericResponse<OrderResponse>> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        setDownloadFinished();
                        onOrderResponse.OnSuccess(response.body().getMessage(),response.body().getData().getMessage(), response.body().getData().getOrderId(), response.body().getData().getCurrentPoints());

                    }else {
                        onOrderResponse.OnError(response.body().getMessage(),response.body().getData().getMessage());
                        setDownloadFinished();
                    }
                } else {
                    onOrderResponse.OnError("Error en el servidor",response.message());
                    setDownloadFinished();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<OrderResponse>> call, Throwable t) {
                onOrderResponse.OnError("Error de conexión", "Algo salió mal, comprueba tu conexión a internet");
            }
        });
    }

    public MutableLiveData<Boolean> getDownloadFinished() {
        return downloadFinished;
    }

    private void setDownloadFinished() {
        downloadFinished.setValue(true);
    }

}
