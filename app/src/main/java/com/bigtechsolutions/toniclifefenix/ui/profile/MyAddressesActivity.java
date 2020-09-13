package com.bigtechsolutions.toniclifefenix.ui.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigtechsolutions.toniclifefenix.R;
import com.bigtechsolutions.toniclifefenix.api.responses.models.Address;
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp;
import com.bigtechsolutions.toniclifefenix.ui.BottomNavigationActivity;
import com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ChooseDeliveryFragment;
import com.bigtechsolutions.toniclifefenix.ui.shoppingcart.StripePayActivity;
import com.bigtechsolutions.toniclifefenix.viewmodel.AddressViewModel;
import com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MyAddressesActivity extends AppCompatActivity implements MyAddressesAdapter.OnEditListener, View.OnClickListener {

    RecyclerView recyclerView;
    MyAddressesAdapter adapter;
    List<Address> addressList;
    Toolbar toolbar;
    ProgressDialog loading;
    AddressViewModel addressViewModel;
    MaterialButton addAddress;
    TextView txtEmptyAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false);

        addressViewModel = new ViewModelProvider(this)
                .get(AddressViewModel.class);

        recyclerView = findViewById(R.id.address_profile_list);
        toolbar = findViewById(R.id.toolbarAddressProfile);
        addAddress = findViewById(R.id.addAddressBtn);
        txtEmptyAddresses = findViewById(R.id.txtEmptyAddressesTwo);

        addAddress.setOnClickListener(this);

        toolbarConfig();

        adapter = new MyAddressesAdapter(
                this,
                addressList,
                this
        );

        recyclerView.setAdapter(adapter);

        loadAddressData();
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private void loadAddressData() {
        addressViewModel.getAddresses().observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(List<Address> addresses) {
                addressList = addresses;
                if(addressList.size() < 1)
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

    private void toolbarConfig() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(R.id.addAddressBtn == id)
        {
            Intent i = new Intent(MyFenixApp.getContext(), UpsertAddressActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onEditClick(int position) {

        int addressId = addressList.get(position).getId();

        Intent i = new Intent(MyFenixApp.getContext(), UpsertAddressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("addressId", addressId);
        i.putExtras(bundle);
        startActivity(i);
        finish();

    }
}