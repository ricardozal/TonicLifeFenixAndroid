package mx.com.bigtechsolutions.toniclifefenix.data.repository;

import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mx.com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import mx.com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.ContentMobileResponse;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Product;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.ui.MyProductRecyclerViewAdapter;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnContentResponse;

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

    public void getContentMobileApp(OnContentResponse onContentResponse){

        Call<List<ContentMobileResponse> > call = authApiService.getContentMobileApp();

        call.enqueue(new Callback<List<ContentMobileResponse> >() {
            @Override
            public void onResponse(Call<List<ContentMobileResponse> > call, Response<List<ContentMobileResponse> > response) {
                if (response.isSuccessful())
                {
                    onContentResponse.OnSuccess(response.body());
                } else {
                    onContentResponse.OnError("Error en el servidor", "Inténtelo más tarde");
                }
            }

            @Override
            public void onFailure(Call<List<ContentMobileResponse> > call, Throwable t) {
                onContentResponse.OnError("Error de conexión", t.getMessage());
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
