package mx.com.bigtechsolutions.toniclifefenix.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.UpsertAddressRequest;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.ui.BottomNavigationActivity;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.AddressViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnAddressResponse;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class UpsertAddressActivity extends AppCompatActivity implements View.OnClickListener {


    Toolbar toolbar;
    ProgressDialog loading;
    AddressViewModel addressViewModel;
    MaterialButton addAddress;

    TextInputLayout Alias;
    TextInputLayout Street;
    TextInputLayout ZipCode;
    TextInputLayout ExtNum;
    TextInputLayout IntNum;
    TextInputLayout Colony;
    TextInputLayout City;
    TextInputLayout State;
    RadioGroup Countries;
    private RadioButton radioMex, radioUsa;

    int addressId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upsert_address);

        addressViewModel = new ViewModelProvider(this)
                .get(AddressViewModel.class);

        toolbar = findViewById(R.id.toolbarAddressProfileUpsert);
        addAddress = findViewById(R.id.saveAddressBtn);
        Alias = findViewById(R.id.addressProfAlias);
        Street = findViewById(R.id.addressProfStreet);
        ZipCode = findViewById(R.id.addressProfZipCode);
        ExtNum = findViewById(R.id.addressProfExtNum);
        IntNum = findViewById(R.id.addressProfIntNum);
        Colony = findViewById(R.id.addressProfColony);
        City = findViewById(R.id.addressProfCity);
        State = findViewById(R.id.addressProfState);
        Countries = findViewById(R.id.addressProfCountries);
        radioMex = findViewById(R.id.mexButtonCountryAddressProf);
        radioUsa = findViewById(R.id.usaButtonCountryAddressProf);

        addAddress.setOnClickListener(this);

        toolbarConfig();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            addressId = bundle.getInt("addressId");
            loadData();
        }

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private void toolbarConfig() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        String title = addressId != 0 ? "Editar dirección" : "Nueva dirección";
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyFenixApp.getContext(), MyAddressesActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void loadData() {

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        addressViewModel.getAddress(addressId, new OnAddressResponse() {
            @Override
            public void OnSuccess(String title, String message, Address address) {

                Alias.getEditText().setText(address.getAlias());
                Street.getEditText().setText(address.getStreet());
                ZipCode.getEditText().setText(address.getZipCode());
                ExtNum.getEditText().setText(address.getExtNum());
                IntNum.getEditText().setText(address.getIntNum());
                Colony.getEditText().setText(address.getColony());
                City.getEditText().setText(address.getCity());
                State.getEditText().setText(address.getState());

                String selected = address.getCountry();

                if(selected != null){

                    if(selected.equals("México")){
                        radioMex.setChecked(true);
                    } else if(selected.equals("Estados Unidos")){
                        radioUsa.setChecked(true);
                    }

                }

                loading.dismiss();

            }

            @Override
            public void OnError(String title, String message) {
                loading.dismiss();
                displayAlert(title, message);
            }
        });

    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(MyFenixApp.getContext(), MyAddressesActivity.class);
                startActivity(i);
                finish();

            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.saveAddressBtn){

            String alias = Alias.getEditText().getText().toString();
            String street = Street.getEditText().getText().toString();
            String zipCode = ZipCode.getEditText().getText().toString();
            String extNum = ExtNum.getEditText().getText().toString();
            String intNum = IntNum.getEditText().getText().toString();
            String colony = Colony.getEditText().getText().toString();
            String city = City.getEditText().getText().toString();
            String state = State.getEditText().getText().toString();
            int countryId = 0;

            switch (Countries.getCheckedRadioButtonId()){
                case R.id.mexButtonCountryAddressProf:
                    countryId = 1;
                    break;
                case R.id.usaButtonCountryAddressProf:
                    countryId = 2;
                    break;
            }

            if(alias.isEmpty()){
                Alias.setError("El alias de la dirección es necesario");
            } else if(street.isEmpty()){
                Street.setError("El nombre de la calle es requerida");
            } else if(zipCode.isEmpty()){
                ZipCode.setError("El código postal es requerido");
            } else if(extNum.isEmpty()){
                ExtNum.setError("El número exterior es requerido");
            } else if(colony.isEmpty()){
                Colony.setError("El nombre de la colonia es requerido");
            } else if(city.isEmpty()){
                City.setError("El nombre de la ciudad es requerida");
            } else if(state.isEmpty()){
                State.setError("El nombre del estado es requerido");
            } else if(countryId == 0){
                Toast.makeText(this, "Debes elegir un país", Toast.LENGTH_LONG).show();
            } else {

                loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

                UpsertAddressRequest upsertAddressRequest = new UpsertAddressRequest(alias,street,zipCode,extNum,intNum,colony,city,state,countryId,addressId);

                addressViewModel.saveAddress(upsertAddressRequest, new OnResponse() {
                    @Override
                    public void OnSuccess(String title, String message) {
                        loading.dismiss();
                        displayAlert(title, message);
                    }

                    @Override
                    public void OnError(String title, String message) {
                        loading.dismiss();
                        displayAlert(title, message);
                    }
                });

            }

        }
    }
}