package mx.com.bigtechsolutions.toniclifefenix.data.repository;


import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import mx.com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import mx.com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.BankData;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.FirebaseTokenRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.GetCandidatesRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.RegisterPointsRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Promotion;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.share_points.SharePointsResponse;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnBankDataResponse;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnSharePointsResponse;

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

    public void getCandidates(GetCandidatesRequest request, OnSharePointsResponse onSharePointsResponse){

        Call<SharePointsResponse> call = authApiService.getCandidates(request);

        call.enqueue(new Callback<SharePointsResponse>() {
            @Override
            public void onResponse(Call<SharePointsResponse> call, Response<SharePointsResponse> response) {
                if (response.isSuccessful())
                {
                    onSharePointsResponse.OnSuccess(response.body());
                } else {
                    onSharePointsResponse.OnError("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<SharePointsResponse> call, Throwable t) {
                onSharePointsResponse.OnError("Error", t.getMessage());
            }
        });

    }

    public void getBankData(OnBankDataResponse onResponse){

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        Call<BankData> call = authApiService.getBankData(distributorId);

        call.enqueue(new Callback<BankData>() {
            @Override
            public void onResponse(Call<BankData> call, Response<BankData> response) {

                if (response.isSuccessful())
                {
                    onResponse.OnSuccess(response.body());
                } else {
                    onResponse.OnError("Algo salió mal", "Error en el servidor");
                }

            }

            @Override
            public void onFailure(Call<BankData> call, Throwable t) {
                onResponse.OnError("Algo salió mal", "Error de conexión");
            }
        });

    }

    public void saveBankData(BankData bankData, OnResponse onResponse) {

        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        Call<GenericResponse<String>> call = authApiService.saveBankData(distributorId, bankData);

        call.enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                if (response.isSuccessful())
                {
                    if (response.body().isSuccess())
                    {
                        onResponse.OnSuccess("Datos guardados", response.body().getMessage());
                    } else {
                        onResponse.OnError("Algo salió mal", response.body().getMessage());
                    }
                } else {
                    onResponse.OnError("Algo salió mal", "Error en el servidor");
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                onResponse.OnError("Algo salió mal", "Error de conexión");
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
