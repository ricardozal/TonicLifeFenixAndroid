package com.bigtechsolutions.toniclifefenix.data.repository;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.SelectAddressRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepository {

    AuthApiService authApiService;
    AuthApiClient authApiClient;
    MutableLiveData<List<Address>> addresses;
    MutableLiveData<List<Branch>> branches;

    public AddressRepository() {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
        addresses = getAddresses();
        branches = getBranches();
    }

    public MutableLiveData<List<Branch>> getBranches() {

        if(branches == null)
        {
            branches = new MutableLiveData<>();
        }

        Call<GenericResponse<List<Branch>>> call = authApiService.getBranches();

        call.enqueue(new Callback<GenericResponse<List<Branch>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Branch>>> call, Response<GenericResponse<List<Branch>>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        branches.setValue(response.body().getData());
                    }
                } else {
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Branch>>> call, Throwable t) {
                Toast.makeText(MyFenixApp.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return branches;

    }

    public MutableLiveData<List<Address>> getAddresses()
    {
        if(addresses == null)
        {
            addresses = new MutableLiveData<>();
        }

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        Call<GenericResponse<List<Address>>> call = authApiService.getAddresses(distributorId);

        call.enqueue(new Callback<GenericResponse<List<Address>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Address>>> call, Response<GenericResponse<List<Address>>> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        addresses.setValue(response.body().getData());
                    }
                } else {
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<List<Address>>> call, Throwable t) {
                Toast.makeText(MyFenixApp.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return addresses;
    }

    public void setSelectedAddress(int addressId, int distributorId)
    {
        SelectAddressRequest selectAddressRequest = new SelectAddressRequest(distributorId, addressId);

        Call<GenericResponse<List<Address>>> call = authApiService.setSelectedAddress(selectAddressRequest);

        call.enqueue(new Callback<GenericResponse<List<Address>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Address>>> call, Response<GenericResponse<List<Address>>> response) {

                if(response.isSuccessful()){

                    if (response.body().isSuccess()){

                        Toast.makeText(MyFenixApp.getContext(), "Domicilio seleccionado", Toast.LENGTH_SHORT).show();

                        addresses.setValue(response.body().getData());

                    } else {

                        Toast.makeText(MyFenixApp.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<List<Address>>> call, Throwable t) {
                Toast.makeText(MyFenixApp.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
