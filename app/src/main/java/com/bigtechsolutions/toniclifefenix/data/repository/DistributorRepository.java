package com.bigtechsolutions.toniclifefenix.data.repository;


import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Promotion;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;

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

    public MutableLiveData<Boolean> getDownloadFinished() {
        return downloadFinished;
    }

    private void setDownloadFinished() {
        downloadFinished.setValue(true);
    }

}
