package com.bigtechsolutions.toniclifefenix.data.repository;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepository {

    AuthApiService authApiService;
    AuthApiClient authApiClient;
    LiveData<List<Address>> addresses;

    public AddressRepository() {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
        addresses = getAddresses();
    }

    public LiveData<List<Address>> getAddresses()
    {
        final MutableLiveData<List<Address>> data = new MutableLiveData<>();

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        Call<GenericResponse<List<Address>>> call = authApiService.getAddresses(distributorId);

        call.enqueue(new Callback<GenericResponse<List<Address>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Address>>> call, Response<GenericResponse<List<Address>>> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        data.setValue(response.body().getData());
                    }
                } else {
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<List<Address>>> call, Throwable t) {
                Toast.makeText(MyFenixApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

        return data;
    }
}
