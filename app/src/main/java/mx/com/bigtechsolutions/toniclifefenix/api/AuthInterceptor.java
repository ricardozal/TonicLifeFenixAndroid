package mx.com.bigtechsolutions.toniclifefenix.api;

import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = SharedPreferencesManager.getStringValue(Constants.ACCESS_TOKEN);

        Request request = chain.request().newBuilder().addHeader("Authorization", token).build();

        return chain.proceed(request);

    }
}
