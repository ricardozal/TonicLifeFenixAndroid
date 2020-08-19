package com.bigtechsolutions.toniclifefenix.data.repository;

import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.ui.MyProductRecyclerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    AuthApiService authApiService;
    AuthApiClient authApiClient;
    LiveData<List<Product>> products;
    private final MutableLiveData<Boolean> downloadFinished = new MutableLiveData<>();

    public ProductRepository()
    {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
        products = getProducts();
    }

    public LiveData<List<Product>> getProducts()
    {
        final MutableLiveData<List<Product>> data = new MutableLiveData<>();

        int countryId = SharedPreferencesManager.getStringValue(Constants.COUNTRY) != null ? Integer.parseInt(SharedPreferencesManager.getStringValue(Constants.COUNTRY)) : SharedPreferencesManager.getIntValue(Constants.DIST_COUNTRY);
        String productName = SharedPreferencesManager.getStringValue(Constants.NAME_PRODUCT_SEARCH) != null ? SharedPreferencesManager.getStringValue(Constants.NAME_PRODUCT_SEARCH) : "0";

        Call<GenericResponse<List<Product>>> call = authApiService.getProducts(countryId, productName);
        call.enqueue(new Callback<GenericResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Product>>> call, Response<GenericResponse<List<Product>>> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        data.setValue(response.body().getData());
                        setDownloadFinished();
                    }
                } else {
                    Toast.makeText(MyFenixApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GenericResponse<List<Product>>> call, Throwable t) {

                Toast.makeText(MyFenixApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });

        return data;
    }

    public MutableLiveData<Boolean> getDownloadFinished() {
        return downloadFinished;
    }

    private void setDownloadFinished() {
        downloadFinished.setValue(true);
    }
}
