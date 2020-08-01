package com.bigtechsolutions.toniclifefenix.data.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.ValidateInvRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.api.responses.models.PaymentMethod;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    AuthApiService authApiService;
    AuthApiClient authApiClient;
    MutableLiveData<List<PaymentMethod>> paymentMethods;
    private final MutableLiveData<Boolean> downloadFinished = new MutableLiveData<>();
    boolean success;

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

    public boolean validateInventory(ValidateInvRequest validateInvRequest){

        Call<GenericResponse<Branch>> call = authApiService.validateInventory(validateInvRequest);

        call.enqueue(new Callback<GenericResponse<Branch>>() {
            @Override
            public void onResponse(Call<GenericResponse<Branch>> call, Response<GenericResponse<Branch>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        success = response.body().isSuccess();
                        SharedPreferencesManager.setIntegerValue(Constants.BRANCH_ID, response.body().getData().getId());
                        setDownloadFinished();
                    }else {
                        success = response.body().isSuccess();
                        Toast.makeText(MyFenixApp.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        setDownloadFinished();
                    }
                } else {
                    success = false;
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    setDownloadFinished();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<Branch>> call, Throwable t) {
                success = false;
                Toast.makeText(MyFenixApp.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                setDownloadFinished();
            }
        });

        return success;

    }

    public MutableLiveData<Boolean> getDownloadFinished() {
        return downloadFinished;
    }

    private void setDownloadFinished() {
        downloadFinished.setValue(true);
    }

}
