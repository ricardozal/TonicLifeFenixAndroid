package com.bigtechsolutions.toniclifefenix.data.repository;


import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.FirebaseTokenRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.RegisterPointsRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Promotion;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorRepository {

    AuthApiService authApiService;
    AuthApiClient authApiClient;
    MutableLiveData<List<Promotion>> promotions;
    private final MutableLiveData<Boolean> downloadFinished = new MutableLiveData<>();

    public DistributorRepository() {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
        promotions = getPromotions();
    }

    public MutableLiveData<List<Promotion>> getPromotions(){

        if(promotions == null){

            promotions = new MutableLiveData<>();

        }

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        Call<GenericResponse<List<Promotion>>> call = authApiService.getPromotions(distributorId);

        call.enqueue(new Callback<GenericResponse<List<Promotion>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Promotion>>> call, Response<GenericResponse<List<Promotion>>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        promotions.setValue(response.body().getData());
                        setDownloadFinished();
                    }
                } else {
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Promotion>>> call, Throwable t) {
                Toast.makeText(MyFenixApp.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return promotions;

    }

    public void saveFirebaseToken(String firebaseToken, OnResponse onResponse){

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        FirebaseTokenRequest firebaseTokenRequest = new FirebaseTokenRequest(firebaseToken);

        Call<GenericResponse<String>> call = authApiService.saveFirebaseToken(distributorId, firebaseTokenRequest);

        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        onResponse.OnSuccess(response.body().getMessage(), response.body().getData());
                    } else {
                        onResponse.OnError(response.body().getMessage(), response.body().getData());
                    }
                } else {
                    onResponse.OnError("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                onResponse.OnError("Error", "Error de conexión");
            }
        });

    }

    public void registerPoints(RegisterPointsRequest request, OnResponse onResponse) {

        Call<GenericResponse<String>> call = authApiService.registerPoints(request);

        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        onResponse.OnSuccess(response.body().getMessage(), response.body().getData());
                    } else {
                        onResponse.OnError(response.body().getMessage(), response.body().getData());
                    }
                } else {
                    onResponse.OnError("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                onResponse.OnError("Error", "Error de conexión");
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
