package com.bigtechsolutions.toniclifefenix.ui.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.commons.Constants;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager;
import com.bigtechsolutions.toniclifefenix.viewmodel.AddressViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShoppingCartAddressesActivity extends AppCompatActivity implements MyAddressRecyclerViewAdapter.OnAddressListener, View.OnClickListener {

    RecyclerView recyclerView;
    MyAddressRecyclerViewAdapter adapter;
    List<Address> addressesList;
    AddressViewModel addressViewModel;
    Toolbar toolbar;
    MaterialButton continueToPayBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_addresses_list);

        addressViewModel = new ViewModelProvider(this)
                .get(AddressViewModel.class);

        recyclerView = findViewById(R.id.addresses_list);
        toolbar = findViewById(R.id.toolbarAddressCart);
        continueToPayBtn = findViewById(R.id.continueToPayBtn);

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
                adapter.setDataList(addresses);
            }
        });

    }

    @Override
    public void OnAddressClick(int position) {

        int addressId = addressesList.get(position).getId();
        int distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID);

        addressViewModel.setSelectedAddress(addressId,distributorId);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(R.id.continueToPayBtn == id)
        {
            Toast.makeText(this, "Resumen", Toast.LENGTH_SHORT).show();
        }
    }
}
