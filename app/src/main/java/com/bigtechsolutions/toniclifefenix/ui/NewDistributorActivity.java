package com.bigtechsolutions.toniclifefenix.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.requests.NewDistributorRequest;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ShoppingCartActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.NewDistributorViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.OnOrderResponse;
import com.bigtechsolutions.toniclifefenix.viewmodel.OnResponse;
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
    NewDistributorViewModel newDistributorViewModel;
    int orderId;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_distributor);

        findViews();
        events();

        newDistributorViewModel = new ViewModelProvider(this)
                .get(NewDistributorViewModel.class);

        Bundle bundle = getIntent().getExtras();
        orderId = bundle.getInt("orderId");

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
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
        String birthday = newDistBirthday.getEditText().getText().toString();
        String birthPlace = newDistBirthPlace.getEditText().getText().toString();
        String nationality = newDistNacionality.getEditText().getText().toString();
        String rfc = newDistRfc.getEditText().getText().toString();
        String curp = newDistCurp.getEditText().getText().toString();
        String phone1 = newDistPhone1.getEditText().getText().toString();
        String phone2 = newDistPhone2.getEditText().getText().toString();
        String identification = newDistIdentification.getEditText().getText().toString();
        String street = newDistStreet.getEditText().getText().toString();
        String zipCode = newDistZipCode.getEditText().getText().toString();
        String extNum = newDistExtNum.getEditText().getText().toString();
        String intNum = newDistIntNum.getEditText().getText().toString();
        String colony = newDistColony.getEditText().getText().toString();
        String city = newDistCity.getEditText().getText().toString();
        String state = newDistState.getEditText().getText().toString();
        String bankName = newDistBankData.getEditText().getText().toString();
        String accountName = newDistAccountName.getEditText().getText().toString();
        String bankAccountNumber = newDistNumAccount.getEditText().getText().toString();
        String clabeRoutingBank = newDistClabe.getEditText().getText().toString();
        String maritalStatus = "";
        int countryId = 0;

        switch (newDistMaritalStatus.getCheckedRadioButtonId()){
            case R.id.singleRadioBtn:
                maritalStatus = "Soltero";
                break;
            case R.id.marriedRadioBtn:
                maritalStatus = "Casado";
                break;
            case R.id.divorcedRadioBtn:
                maritalStatus = "Divorciado";
                break;
            case R.id.windowerRadioBtn:
                maritalStatus = "Viudo";
                break;
        }

        switch (newDistCountries.getCheckedRadioButtonId()){
            case R.id.mexButtonCountry:
                countryId = 1;
                break;
            case R.id.usaButtonCountry:
                countryId = 2;
                break;
        }

        if (name.isEmpty()){
            newDistName.setError("El nombre completo es requerido");
        } else if(email.isEmpty()){
            newDistEmail.setError("La correo electrónico es requerido");
        } else if(birthday.isEmpty()){
            newDistBirthday.setError("La fecha de nacimiento es requerida");
        } else if(birthPlace.isEmpty()){
            newDistBirthPlace.setError("El lugar de nacimiento es requerido");
        } else if(nationality.isEmpty()){
            newDistNacionality.setError("La nacionalidad es requerida");
        } else if(rfc.isEmpty()){
            newDistRfc.setError("El RFC es requeridp");
        } else if(curp.isEmpty()){
            newDistCurp.setError("La CURP es requerida");
        } else if(phone1.isEmpty()){
            newDistPhone1.setError("El teléfono es requerido");
        } else if(phone2.isEmpty()){
            newDistPhone2.setError("El teléfono es requerido");
        } else if(identification.isEmpty()){
            newDistIdentification.setError("El número de identificación e requerido");
        } else if(street.isEmpty()){
            newDistStreet.setError("El nombre de la calle es requerida");
        } else if(zipCode.isEmpty()){
            newDistZipCode.setError("El código postal es requerido");
        } else if(extNum.isEmpty()){
            newDistExtNum.setError("El número exterior es requerido");
        } else if(colony.isEmpty()){
            newDistColony.setError("El nombre de la colonia es requerido");
        } else if(city.isEmpty()){
            newDistCity.setError("El nombre de la ciudad es requerida");
        } else if(state.isEmpty()){
            newDistState.setError("El nombre del estado es requerido");
        } else if(bankName.isEmpty()){
            newDistBankData.setError("El nombre del banco es requerido");
        } else if(accountName.isEmpty()){
            newDistAccountName.setError("El nombre del propietario es requerido");
        } else if(bankAccountNumber.isEmpty()){
            newDistNumAccount.setError("El número de cuenta es requerido");
        } else if(clabeRoutingBank.isEmpty()){
            newDistClabe.setError("La CLABE es requerida");
        } else if(maritalStatus.isEmpty()){
            Toast.makeText(this, "Debes elegir un estado civil", Toast.LENGTH_LONG).show();
        } else if(countryId == 0){
            Toast.makeText(this, "Debes elegir un país", Toast.LENGTH_LONG).show();
        } else {
            loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);
            NewDistributorRequest request = new NewDistributorRequest(street, zipCode, extNum, intNum, colony, city, state, countryId, name, email, maritalStatus, birthday, birthPlace, nationality, rfc, curp, phone1, phone2, identification, orderId, bankName, accountName, bankAccountNumber, clabeRoutingBank);

            newDistributorViewModel.saveNewDistributor(request, new OnResponse() {
                @Override
                public void OnSuccess(String title, String message) {
                    loading.dismiss();
                    displayAlert(
                            title,
                            message,
                            true
                    );
                }

                @Override
                public void OnError(String title, String message) {
                    loading.dismiss();
                    displayAlert(
                            title,
                            message,
                            false
                    );
                }
            });

        }

    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean correct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(correct){
                    Intent i = new Intent(MyFenixApp.getContext(), BottomNavigationActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    dialog.dismiss();
                }

            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

}