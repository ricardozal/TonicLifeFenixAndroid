package com.bigtechsolutions.toniclifefenix.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.AuthApiClient;
import com.bigtechsolutions.toniclifefenix.api.AuthApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class NewDistributorActivity extends AppCompatActivity implements View.OnClickListener  {

    TextInputLayout newDistName;
    TextInputLayout newDistEmail;
    RadioGroup newDistMaritalStatus;
    TextInputLayout newDistBirthday;
    TextInputLayout newDistBirthPlace;
    TextInputLayout newDistNacionality;
    TextInputLayout newDistRfc;
    TextInputLayout newDistCurp;
    TextInputLayout newDistPhone1;
    TextInputLayout newDistPhone2;
    TextInputLayout newDistIdentification;
    TextInputLayout newDistStreet;
    TextInputLayout newDistZipCode;
    TextInputLayout newDistExtNum;
    TextInputLayout newDistIntNum;
    TextInputLayout newDistColony;
    TextInputLayout newDistCity;
    TextInputLayout newDistState;
    RadioGroup newDistCountries;
    TextInputLayout newDistBankData;
    TextInputLayout newDistAccountName;
    TextInputLayout newDistNumAccount;
    TextInputLayout newDistClabe;
    MaterialButton saveNewDistBtn;

    AuthApiClient authApiClient;
    AuthApiService authApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_distributor);

        findViews();
        events();
        retrofitInit();

    }

    private void retrofitInit() {
        authApiClient = AuthApiClient.getInstance();
        authApiService = authApiClient.getAuthApiService();
    }

    private void events() {

        saveNewDistBtn.setOnClickListener(this);
    }

    private void findViews() {
        newDistName = findViewById(R.id.newDistName);
        newDistEmail = findViewById(R.id.newDistEmail);
        newDistMaritalStatus = findViewById(R.id.newDistMaritalStatus);
        newDistBirthday = findViewById(R.id.newDistBirthday);
        newDistBirthPlace = findViewById(R.id.newDistBirthPlace);
        newDistNacionality = findViewById(R.id.newDistNacionality);
        newDistRfc = findViewById(R.id.newDistRfc);
        newDistCurp = findViewById(R.id.newDistCurp);
        newDistPhone1 = findViewById(R.id.newDistPhone1);
        newDistPhone2 = findViewById(R.id.newDistPhone2);
        newDistIdentification = findViewById(R.id.newDistIdentification);
        newDistStreet = findViewById(R.id.newDistStreet);
        newDistZipCode = findViewById(R.id.newDistZipCode);
        newDistExtNum = findViewById(R.id.newDistExtNum);
        newDistIntNum = findViewById(R.id.newDistIntNum);
        newDistColony = findViewById(R.id.newDistColony);
        newDistCity = findViewById(R.id.newDistCity);
        newDistState = findViewById(R.id.newDistState);
        newDistCountries = findViewById(R.id.newDistCountries);
        newDistBankData = findViewById(R.id.newDistBankData);
        newDistAccountName = findViewById(R.id.newDistAccountName);
        newDistNumAccount = findViewById(R.id.newDistNumAccount);
        newDistClabe = findViewById(R.id.newDistClabe);
        saveNewDistBtn = findViewById(R.id.saveNewDistBtn);
    }

    @Override
    public void onClick(View v) {

        String name = newDistName.getEditText().getText().toString();
        String email = newDistEmail.getEditText().getText().toString();

        if (name.isEmpty()){
            newDistName.setError("El ID Tonic Life es requerido");
        } else if(email.isEmpty()){
            newDistEmail.setError("La contraseña es requerida");
        }

    }
}