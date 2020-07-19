package com.bigtechsolutions.toniclifefenix.data.repository;

import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.ui.MyProductRecyclerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    AuthApiService authApiService;
    AuthApiClient authApiClient;
    LiveData<List<Product>> products;

    public ProductRepository()
    {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
        products = getProducts();
    }

    public LiveData<List<Product>> getProducts()
    {
        final MutableLiveData<List<Product>> data = new MutableLiveData<>();

        Call<GenericResponse<List<Product>>> call = authApiService.getProducts();
        call.enqueue(new Callback<GenericResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Product>>> call, Response<GenericResponse<List<Product>>> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        data.setValue(response.body().getData());
                    }
                } else {
                    Toast.makeText(MyFenixApp.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<List<Product>>> call, Throwable t) {

                Toast.makeText(MyFenixApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

        return data;
    }

}