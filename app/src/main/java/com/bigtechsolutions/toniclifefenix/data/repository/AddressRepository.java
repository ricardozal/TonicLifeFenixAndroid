package com.bigtechsolutions.toniclifefenix.data.repository;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.SelectAddressRequest;
import com.bigtechsolutions.toniclifefenix.api.requests.UpsertAddressRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Branch;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnAddressResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;

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
    private final MutableLiveData<Boolean> downloadFinished = new MutableLiveData<>();

    public AddressRepository() {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
        addresses = getAddresses();
        branches = getBranches();
    }

    public void saveAddress(UpsertAddressRequest upsertAddressRequest, OnResponse onResponse){

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        Call<GenericResponse<String>> call = authApiService.saveAddress(distributorId, upsertAddressRequest);

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
                    onResponse.OnError("Algo salió mal", response.message());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                onResponse.OnError("Algo salió mal", "Error de conexión");

            }
        });

    }

    public void getAddress(int addressId, OnAddressResponse onResponse){

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        Call<GenericResponse<Address>> call = authApiService.getAddress(addressId, distributorId);

        call.enqueue(new Callback<GenericResponse<Address>>() {
            @Override
            public void onResponse(Call<GenericResponse<Address>> call, Response<GenericResponse<Address>> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        onResponse.OnSuccess("Todo bien",response.body().getMessage(), response.body().getData());
                    }
                } else {
                    onResponse.OnError("Algo salió mal", "Error en el servidor");
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<Address>> call, Throwable t) {
                onResponse.OnError("Algo salió mal", "Error de conexión");
            }
        });

    }

    public MutableLiveData<List<Branch>> getBranches() {

        if(branches == null)
        {
            branches = new MutableLiveData<>();
        }

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        Call<GenericResponse<List<Branch>>> call = authApiService.getBranches(distributorId);

        call.enqueue(new Callback<GenericResponse<List<Branch>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Branch>>> call, Response<GenericResponse<List<Branch>>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        branches.setValue(response.body().getData());
                        setDownloadFinished();
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
                        setDownloadFinished();
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
        downloadFinished.setValue(false);

        SelectAddressRequest selectAddressRequest = new SelectAddressRequest(distributorId, addressId);

        Call<GenericResponse<List<Address>>> call = authApiService.setSelectedAddress(selectAddressRequest);

        call.enqueue(new Callback<GenericResponse<List<Address>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Address>>> call, Response<GenericResponse<List<Address>>> response) {

                if(response.isSuccessful()){

                    if (response.body().isSuccess()){

                        addresses.setValue(response.body().getData());
                        setDownloadFinished();

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

    public MutableLiveData<Boolean> getDownloadFinished() {
        return downloadFinished;
    }

    private void setDownloadFinished() {
        downloadFinished.setValue(true);
    }
}
