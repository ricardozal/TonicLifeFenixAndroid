package com.bigtechsolutions.toniclifefenix.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.ApiClient;
import com.bigtechsolutions.toniclifefenix.api.ApiService;
import com.bigtechsolutions.toniclifefenix.api.requests.AuthRequest;
import com.bigtechsolutions.toniclifefenix.api.responses.GenericResponse;
import com.bigtechsolutions.toniclifefenix.api.responses.Token;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.ui.BottomNavigationActivity;
import com.bigtechsolutions.toniclifefenix.ui.ChooseCountryFragment;
import com.bigtechsolutions.toniclifefenix.ui.WaitingActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout etTonicLifeId, etPassword;
    MaterialButton btnLogin;
    CheckBox checkRemember;
    ApiClient apiClient;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = SharedPreferencesManager.getStringValue(Constants.ACCESS_TOKEN);

        if(token != null)
        {
            Intent i = new Intent(MainActivity.this, BottomNavigationActivity.class);
            startActivity(i);
            finish();
        }


        findViews();
        events();
        retrofitInit();

    }


    private void findViews() {
        btnLogin = findViewById(R.id.loginBtn);
        etTonicLifeId = findViewById(R.id.idTonicLife);
        etPassword = findViewById(R.id.password);
        checkRemember = findViewById(R.id.remember);
    }

    private void events() {

        btnLogin.setOnClickListener(this);

    }



    private void retrofitInit() {

        apiClient = ApiClient.getInstance();
        apiService = apiClient.getApiService();

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.loginBtn) {
            login();
        }

    }

    private void login() {

        String tonicLifeId = etTonicLifeId.getEditText().getText().toString();
        String password = etPassword.getEditText().getText().toString();

        if (tonicLifeId.isEmpty()){
            etTonicLifeId.setError("El ID Tonic Life es requerido");
        } else if(password.isEmpty()){
            etPassword.setError("La contraseña es requerida");
        } else{

            AuthRequest authRequest = new AuthRequest(tonicLifeId, password, checkRemember.isChecked());

            Call<GenericResponse<Token>> call = apiService.login(authRequest);

            final ProgressDialog loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);


            call.enqueue(new Callback<GenericResponse<Token>>() {
                @Override
                public void onResponse(Call<GenericResponse<Token>> call, Response<GenericResponse<Token>> response) {

                    if(response.isSuccessful()){

                        if (response.body().isSuccess())
                        {

                            String token = response.body().getData().getTokenType() + " " + response.body().getData().getAccessToken();

                            SharedPreferencesManager
                                    .setIntegerValue(Constants.DISTRIBUTOR_ID, response.body().getData().getId());
                            SharedPreferencesManager
                                    .setStringValue(Constants.DISTRIBUTOR_NAME, response.body().getData().getName());
                            SharedPreferencesManager
                                    .setStringValue(Constants.DISTRIBUTOR_EMAIL, response.body().getData().getEmail());
                            SharedPreferencesManager
                                    .setStringValue(Constants.DISTRIBUTOR_TONIC_LIFE_ID, response.body().getData().getTonicLifeId());
                            SharedPreferencesManager
                                    .setStringValue(Constants.ACCESS_TOKEN, token);

                            loading.dismiss();

                            Intent i = new Intent(MainActivity.this, WaitingActivity.class);
                            startActivity(i);
                            finish();

                        } else{
                            loading.dismiss();
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        loading.dismiss();
                        Toast.makeText(MainActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<GenericResponse<Token>> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(MainActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}