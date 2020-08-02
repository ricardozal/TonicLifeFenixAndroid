package com.bigtechsolutions.toniclifefenix.data.repository;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.GenerateIntentRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.OrderRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.ValidateInvRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.ui.shoppingcart.PaymentMethodActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.OnOrderResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.OnSuccess;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    AuthApiService authApiService;
    AuthApiClient authApiClient;
    MutableLiveData<List<PaymentMethod>> paymentMethods;
    private final MutableLiveData<Boolean> downloadFinished = new MutableLiveData<>();


    public OrderRepository() {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
        paymentMethods = getPaymentMethods();
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

    public void validateInventory(ValidateInvRequest validateInvRequest, OnSuccess onSuccess){

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
                        onSuccess.OnRequestSuccess();

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
            public void onFailure(Call<GenericResponse<Branch>> call, Throwable t) {
                Toast.makeText(MyFenixApp.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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

        Call<GenericResponse<String>> call = authApiService.saveOrder(orderRequest);

        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        setDownloadFinished();
                        onOrderResponse.OnSuccess(response.body().getMessage(),response.body().getData());

                    }else {
                        onOrderResponse.OnError(response.body().getMessage(),response.body().getData());
                        setDownloadFinished();
                    }
                } else {
                    onOrderResponse.OnError("Error en el servidor",response.message());
                    setDownloadFinished();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
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
