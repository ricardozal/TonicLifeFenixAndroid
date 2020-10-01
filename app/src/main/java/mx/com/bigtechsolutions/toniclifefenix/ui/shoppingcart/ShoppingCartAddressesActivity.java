package mx.com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import mx.com.bigtechsolutions.toniclifefenix.R;
import mx.com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import mx.com.bigtechsolutions.toniclifefenix.commons.Constants;
import mx.com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import mx.com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import mx.com.bigtechsolutions.toniclifefenix.viewmodel.AddressViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShoppingCartAddressesActivity extends AppCompatActivity implements MyAddressRecyclerViewAdapter.OnAddressListener, View.OnClickListener {

    RecyclerView recyclerView;
    MyAddressRecyclerViewAdapter adapter;
    List<Address> addressesList;
    AddressViewModel addressViewModel;
    Toolbar toolbar;
    MaterialButton continueToPayBtn;
    ProgressDialog loading;
    TextView txtEmptyAddresses;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_addresses_list);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);


        addressViewModel = new ViewModelProvider(this)
                .get(AddressViewModel.class);

        recyclerView = findViewById(R.id.addresses_list);
        toolbar = findViewById(R.id.toolbarAddressCart);
        continueToPayBtn = findViewById(R.id.continueToPayBtn);
        txtEmptyAddresses = findViewById(R.id.txtEmptyAddresses);

        continueToPayBtn.setOnClickListener(this);

        toolbarConfig();

        adapter = new MyAddressRecyclerViewAdapter(
                this,
                addressesList,
                this
        );

        recyclerView.setAdapter(adapter);

        loadAddressData();
    }

    private void toolbarConfig() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyFenixApp.getContext(), ShoppingCartActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void loadAddressData() {

        addressViewModel.getAddresses().observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(List<Address> addresses) {
                addressesList = addresses;
                if(addressesList.size() < 1)
                    txtEmptyAddresses.setVisibility(View.VISIBLE);
                adapter.setDataList(addresses);
            }
        });

        addressViewModel.getDownloadFinished().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean downloadFinished) {
                if (downloadFinished != null) {
                    if (downloadFinished) {
                        loading.dismiss();
                    }
                }
            }
        });

    }

    @Override
    public void OnAddressClick(int position) {

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        int addressId = addressesList.get(position).getId();
        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        addressViewModel.setSelectedAddress(addressId,distributorId);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(R.id.continueToPayBtn == id)
        {
            Intent i = new Intent(MyFenixApp.getContext(), CheckOutActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("branchSelected", false);
            i.putExtras(bundle);
            startActivity(i);
            finish();
        }
    }
}
