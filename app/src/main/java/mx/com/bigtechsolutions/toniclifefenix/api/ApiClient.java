package mx.com.bigtechsolutions.toniclifefenix.api;

import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiClient instance = null;
    private ApiService apiService;
    private Retrofit retrofit;

    public ApiClient()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_TONIC_LIFE_FENIX_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static ApiClient getInstance()
    {
        if (instance == null)
        {
            instance = new ApiClient();
        }

        return instance;
    }

    public ApiService getApiService()
    {
        return apiService;
    }

}
