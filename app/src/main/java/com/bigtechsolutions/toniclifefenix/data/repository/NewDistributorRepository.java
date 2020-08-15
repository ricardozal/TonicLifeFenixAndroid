package com.bigtechsolutions.toniclifefenix.data.repository;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.NewDistributorRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewDistributorRepository {

    AuthApiService authApiService;
    AuthApiClient authApiClient;

    public NewDistributorRepository() {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
    }

    public void saveNewDistributor(NewDistributorRequest newDistributor, OnResponse onResponse){

        Call<GenericResponse<String>> call = authApiService.saveNewDistributor(newDistributor);

        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {

                if(response.isSuccessful()){

                    if (response.body().isSuccess()){
                        onResponse.OnSuccess(response.body().getMessage(), response.body().getData());
                    } else {
                        onResponse.OnError(response.body().getMessage(), response.body().getData());
                    }

                } else {
                    onResponse.OnError(response.body().getMessage(), response.body().getData());

                }

            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                onResponse.OnError("Error de conexión", "Intentelo más tarde");
            }
        });

    }
}
