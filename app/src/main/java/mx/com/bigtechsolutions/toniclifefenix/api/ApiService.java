package mx.com.bigtechsolutions.toniclifefenix.api;

import mx.com.bigtechsolutions.toniclifefenix.api.requests.AuthRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("auth/login")
    Call<GenericResponse<Token>> login(@Body AuthRequest request);

}
