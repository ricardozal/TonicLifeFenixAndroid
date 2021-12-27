package mx.com.bigtechsolutions.toniclifefenix.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.requests.BankData;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.AddressViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.DistributorViewModel;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnBankDataResponse;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MyBankDataActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextInputLayout bankName, ownerName, countNumber;
    MaterialButton saveBankData;

    DistributorViewModel distributorViewModel;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank_data);

        distributorViewModel = new ViewModelProvider(this)
                .get(DistributorViewModel.class);

        toolbar = findViewById(R.id.toolbarMyBankData);
        bankName = findViewById(R.id.bankName);
        ownerName = findViewById(R.id.ownerName);
        countNumber = findViewById(R.id.countNumber);

        saveBankData = findViewById(R.id.saveBankDataBtn);
        saveBankData.setOnClickListener(this);

        toolbarConfig();

        loadData();
    }

    private void loadData() {
        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        distributorViewModel.getBankData(new OnBankDataResponse() {
            @Override
            public void OnSuccess(BankData bankData) {

                bankName.getEditText().setText(bankData.getBankName());
                ownerName.getEditText().setText(bankData.getBankOwnerName());
                countNumber.getEditText().setText(bankData.getBankAccountNumber());

                loading.dismiss();
            }

            @Override
            public void OnError(String title, String message) {
                loading.dismiss();
                displayAlert(title, message);
            }
        });
    }

    private void toolbarConfig() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setTitle("Datos bancarios");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.saveBankDataBtn) {

            String bankNameStr = bankName.getEditText().getText().toString();
            String ownerNameStr = ownerName.getEditText().getText().toString();
            String countNumberStr = countNumber.getEditText().getText().toString();

            if (bankNameStr.isEmpty()) {
                bankName.setError("El nombre del banco es necesario");
            } else if (ownerNameStr.isEmpty()) {
                ownerName.setError("El nombre del propietario es necesario");
            } else if (countNumberStr.isEmpty()) {
                countNumber.setError("El número de cuenta es necesario");
            } else {
                loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

                BankData bankData = new BankData(bankNameStr, ownerNameStr, countNumberStr);
                distributorViewModel.saveBankData(bankData, new OnResponse() {
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